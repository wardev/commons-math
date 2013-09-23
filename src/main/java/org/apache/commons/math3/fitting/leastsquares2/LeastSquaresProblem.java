package org.apache.commons.math3.fitting.leastsquares2;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * The data necessary to define a non-linear least squares problem. Includes the observed
 * values, computed model function, and convergence/divergence criteria. Weights are
 * implicit in {@link Evaluation#computeResiduals()} and {@link
 * Evaluation#computeJacobian()}.
 *
 * @version $Id$
 */
public interface LeastSquaresProblem extends OptimizationProblem<LeastSquaresProblem.Evaluation> {

    /**
     * Gets the initial guess.
     *
     * @return the initial guess values.
     */
    RealVector getStart();

    /**
     * Get the number of observations (rows in the Jacobian) in this problem.
     *
     * @return the number of scalar observations
     */
    int getObservationSize();

    /**
     * Get the number of parameters (columns in the Jacobian) in this problem.
     *
     * @return the number of scalar parameters
     */
    int getParameterSize();

    /**
     * Evaluate the model at the specified point.
     *
     *
     * @param point the parameter values.
     * @return the model's value and derivative at the given point.
     * @throws org.apache.commons.math3.exception.TooManyEvaluationsException
     *          if the maximal number of evaluations (of the model vector function) is
     *          exceeded.
     */
    Evaluation evaluate(RealVector point);

    /**
     * An evaluation of a {@link LeastSquaresProblem} at a particular point. This class
     * also computes several quantities derived from the value and its Jacobian.
     */
    public interface Evaluation {

        /**
         * Get the covariance matrix of the optimized parameters. <br/> Note that this
         * operation involves the inversion of the <code>J<sup>T</sup>J</code> matrix,
         * where {@code J} is the Jacobian matrix. The {@code threshold} parameter is a
         * way for the caller to specify that the result of this computation should be
         * considered meaningless, and thus trigger an exception.
         *
         *
         * @param threshold Singularity threshold.
         * @return the covariance matrix.
         * @throws org.apache.commons.math3.linear.SingularMatrixException
         *          if the covariance matrix cannot be computed (singular problem).
         */
        RealMatrix computeCovariances(double threshold);

        /**
         * Computes an estimate of the standard deviation of the parameters. The returned
         * values are the square root of the diagonal coefficients of the covariance
         * matrix, {@code sd(a[i]) ~= sqrt(C[i][i])}, where {@code a[i]} is the optimized
         * value of the {@code i}-th parameter, and {@code C} is the covariance matrix.
         *
         *
         * @param covarianceSingularityThreshold Singularity threshold (see {@link
         *                                       #computeCovariances(double) computeCovariances}).
         * @return an estimate of the standard deviation of the optimized parameters
         * @throws org.apache.commons.math3.linear.SingularMatrixException
         *          if the covariance matrix cannot be computed.
         */
        RealVector computeSigma(double covarianceSingularityThreshold);

        /**
         * Computes the normalized cost. It is the square-root of the sum of squared of
         * the residuals, divided by the number of measurements.
         *
         * @return the cost.
         */
        double computeRMS();

        /**
         * Computes the weighted objective (model) function value.
         *
         * @return the weighted objective function value at the specified point.
         */
        RealVector computeValue();

        /**
         * Computes the weighted Jacobian matrix.
         *
         * @return the weighted Jacobian: W<sup>1/2</sup> J.
         * @throws DimensionMismatchException if the Jacobian dimension does not match
         *                                    problem dimension.
         */
        RealMatrix computeJacobian();

        /**
         * Computes the cost.
         *
         * @return the cost.
         * @see #computeResiduals()
         */
        double computeCost();

        /**
         * Computes the weighted residuals. The residual is the difference between the
         * observed (target) values and the model (objective function) value. There is one
         * residual for each element of the vector-valued function. The raw residuals are
         * then multiplied by the square root of the weight matrix.
         *
         * @return the weighted residuals: W<sup>1/2</sup> K.
         * @throws DimensionMismatchException if the residuals have the wrong length.
         */
        RealVector computeResiduals();

        /**
         * Get the abscissa (independent variables) of this evaluation.
         *
         * @return the point provided to {@link #evaluate(RealVector)}.
         */
        RealVector getPoint();
    }
}
