/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.apache.commons.math3.fitting.leastsquares2;

import org.apache.commons.math3.TestUtils;
import org.apache.commons.math3.fitting.leastsquares2.LeastSquaresProblem.Evaluation;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Pair;
import org.apache.commons.math3.util.Precision;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * The only features tested here are utility methods defined
 * in {@link LeastSquaresProblem.Evaluation} that compute the
 * chi-square and parameters standard-deviations.
 */
public class EvaluationTest {

    /**
     * Create a {@link LeastSquaresBuilder} from a {@link StatisticalReferenceDataset}.
     *
     * @param dataset the source data
     * @return a builder for further customization.
     */
    public LeastSquaresBuilder builder(StatisticalReferenceDataset dataset) {
        StatisticalReferenceDataset.LeastSquaresProblem problem
                = dataset.getLeastSquaresProblem();
        final double[] start = dataset.getParameters();
        final double[] observed = dataset.getData()[1];
        final double[] weights = new double[observed.length];
        Arrays.fill(weights, 1d);

        return new LeastSquaresBuilder()
                .model(problem.getModelFunction(), problem.getModelFunctionJacobian())
                .target(observed)
                .weight(new DiagonalMatrix(weights))
                .start(start);
    }

    @Test
    public void testComputeResiduals() {
        //setup
        RealVector point = new ArrayRealVector(2);
        Evaluation evaluation = new LeastSquaresBuilder()
                .target(new ArrayRealVector(new double[]{3,-1}))
                .model(new MultivariateJacobianFunction() {
                    public Pair<RealVector, RealMatrix> value(RealVector point) {
                        return new Pair<RealVector, RealMatrix>(
                                new ArrayRealVector(new double[]{1, 2}),
                                MatrixUtils.createRealIdentityMatrix(2)
                        );
                    }
                })
                .weight(MatrixUtils.createRealIdentityMatrix(2))
                .build()
                .evaluate(point);

        //action + verify
        Assert.assertArrayEquals(
                evaluation.computeResiduals().toArray(),
                new double[]{2, -3},
                Precision.EPSILON);
    }

    @Test
    public void testComputeCovariance() throws IOException {
        //setup
        RealVector point = new ArrayRealVector(2);
        Evaluation evaluation = new LeastSquaresBuilder()
                .model(new MultivariateJacobianFunction() {
                    public Pair<RealVector, RealMatrix> value(RealVector point) {
                        return new Pair<RealVector, RealMatrix>(
                                new ArrayRealVector(2),
                                MatrixUtils.createRealDiagonalMatrix(new double[]{1, 1e-2})
                        );
                    }
                })
                .weight(MatrixUtils.createRealDiagonalMatrix(new double[]{1, 1}))
                .target(new ArrayRealVector(2))
                .build()
                .evaluate(point);

        //action
        TestUtils.assertEquals(
                "covariance",
                evaluation.computeCovariances(FastMath.nextAfter(1e-4, 0.0)),
                MatrixUtils.createRealMatrix(new double[][]{{1, 0}, {0, 1e4}}),
                Precision.EPSILON
        );

        //singularity fail
        try {
            evaluation.computeCovariances(FastMath.nextAfter(1e-4, 1.0));
            Assert.fail("Expected Exception");
        } catch (SingularMatrixException e) {
            //expected
        }
    }

    @Test
    public void testComputeValueAndJacobian() {
        //setup
        final RealVector point = new ArrayRealVector(new double[]{1, 2});
        Evaluation evaluation = new LeastSquaresBuilder()
                .weight(new DiagonalMatrix(new double[]{16, 4}))
                .model(new MultivariateJacobianFunction() {
                    public Pair<RealVector, RealMatrix> value(RealVector actualPoint) {
                        //verify correct values passed in
                        Assert.assertArrayEquals(
                                point.toArray(), actualPoint.toArray(), Precision.EPSILON);
                        //return values
                        return new Pair<RealVector, RealMatrix>(
                                new ArrayRealVector(new double[]{3, 4}),
                                MatrixUtils.createRealMatrix(new double[][]{{5, 6}, {7, 8}})
                        );
                    }
                })
                .target(new double[2])
                .build()
                .evaluate(point);

        //action
        RealVector value = evaluation.computeValue();
        RealMatrix jacobian = evaluation.computeJacobian();

        //verify
        Assert.assertArrayEquals(evaluation.getPoint().toArray(), point.toArray(), 0);
        Assert.assertArrayEquals(new double[]{12, 8}, value.toArray(), Precision.EPSILON);
        TestUtils.assertEquals(
                "jacobian",
                jacobian,
                MatrixUtils.createRealMatrix(new double[][]{{20, 24},{14, 16}}),
                Precision.EPSILON);
    }

    @Test
    public void testComputeCost() throws IOException {
        final StatisticalReferenceDataset dataset
            = StatisticalReferenceDatasetFactory.createKirby2();

        final LeastSquaresProblem lsp = builder(dataset).build();

        final double expected = dataset.getResidualSumOfSquares();
        final double cost = lsp.evaluate(lsp.getStart()).computeCost();
        final double actual = cost * cost;
        Assert.assertEquals(dataset.getName(), expected, actual, 1e-11 * expected);
    }

    @Test
    public void testComputeRMS() throws IOException {
        final StatisticalReferenceDataset dataset
            = StatisticalReferenceDatasetFactory.createKirby2();

        final LeastSquaresProblem lsp = builder(dataset).build();

        final double expected = FastMath.sqrt(dataset.getResidualSumOfSquares() /
                                              dataset.getNumObservations());
        final double actual = lsp.evaluate(lsp.getStart()).computeRMS();
        Assert.assertEquals(dataset.getName(), expected, actual, 1e-11 * expected);
    }

    @Test
    public void testComputeSigma() throws IOException {
        final StatisticalReferenceDataset dataset
            = StatisticalReferenceDatasetFactory.createKirby2();

        final LeastSquaresProblem lsp = builder(dataset).build();

        final double[] expected = dataset.getParametersStandardDeviations();

        final Evaluation evaluation = lsp.evaluate(lsp.getStart());
        final double cost = evaluation.computeCost();
        final RealVector sig = evaluation.computeSigma(1e-14);
        final int dof = lsp.getObservationSize() - lsp.getParameterSize();
        for (int i = 0; i < sig.getDimension(); i++) {
            final double actual = FastMath.sqrt(cost * cost / dof) * sig.getEntry(i);
            Assert.assertEquals(dataset.getName() + ", parameter #" + i,
                                expected[i], actual, 1e-6 * expected[i]);
        }
    }
}
