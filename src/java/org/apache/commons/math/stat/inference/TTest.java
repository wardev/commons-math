/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math.stat.inference;

import org.apache.commons.math.MathException;
import org.apache.commons.math.stat.univariate.StatisticalSummary;

/**
 * An interface for Student's t-tests.
 *
 * @version $Revision: 1.6 $ $Date: 2004/06/23 16:26:14 $ 
 */
public interface TTest {
    
    
    /**
     * Computes a paired, 2-sample t-statistic based on the data in the input 
     * arrays.  The t-statistic returned is equivalent to what would be returned by
     * computing the one-sample t-statistic {@link #t(double, double[])}, with
     * <code>mu = 0</code> and the sample array consisting of the (signed) 
     * differences between corresponding entries in <code>sample1</code> and 
     * <code>sample2.</code>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The input arrays must have the same length and their common length
     * must be at least 2.
     * </li></ul>
     *
     * @param sample1 array of sample data values
     * @param sample2 array of sample data values
     * @return t statistic
     * @throws IllegalArgumentException if the precondition is not met
     * @throws MathException if the statistic can not be computed do to a
     *         convergence or other numerical error.
     */
    double pairedT(double[] sample1, double[] sample2) 
    throws IllegalArgumentException, MathException;
    
    /**
     * Returns the <i>observed significance level</i>, or 
     * <a href="http://www.cas.lancs.ac.uk/glossary_v1.1/hyptest.html#pvalue">
     * p-value</a>, associated with a paired, two-sample, two-tailed t-test 
     * based on the data in the input arrays.
     * <p>
     * The number returned is the smallest significance level
     * at which one can reject the null hypothesis that the mean of the paired
     * differences is 0 in favor of the two-sided alternative that the mean paired 
     * difference is not equal to 0. For a one-sided test, divide the returned 
     * value by 2.
     * <p>
     * This test is equivalent to a one-sample t-test computed using
     * {@link #tTest(double, double[])} with <code>mu = 0</code> and the sample
     * array consisting of the signed differences between corresponding elements of 
     * <code>sample1</code> and <code>sample2.</code>
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the p-value depends on the assumptions of the parametric
     * t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/ttest_unpaired_ass_viol.html">
     * here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The input array lengths must be the same and their common length must
     * be at least 2.
     * </li></ul>
     *
     * @param sample1 array of sample data values
     * @param sample2 array of sample data values
     * @return p-value for t-test
     * @throws IllegalArgumentException if the precondition is not met
     * @throws MathException if an error occurs computing the p-value
     */
    double pairedTTest(double[] sample1, double[] sample2)
    throws IllegalArgumentException, MathException;
    
    /**
     * Performs a paired t-test</a> evaluating the null hypothesis that the 
     * mean of the paired differences between <code>sample1</code> and
     * <code>sample2</code> is 0 in favor of the two-sided alternative that the 
     * mean paired difference is not equal to 0, with significance level 
     * <code>alpha</code>.
     * <p>
     * Returns <code>true</code> iff the null hypothesis can be rejected with 
     * confidence <code>1 - alpha</code>.  To perform a 1-sided test, use 
     * <code>alpha / 2</code>
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the test depends on the assumptions of the parametric
     * t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/ttest_unpaired_ass_viol.html">
     * here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The input array lengths must be the same and their common length 
     * must be at least 2.
     * </li>
     * <li> <code> 0 < alpha < 0.5 </code>
     * </li></ul>
     *
     * @param sample1 array of sample data values
     * @param sample2 array of sample data values
     * @param alpha significance level of the test
     * @return true if the null hypothesis can be rejected with 
     * confidence 1 - alpha
     * @throws IllegalArgumentException if the preconditions are not met
     * @throws MathException if an error occurs performing the test
     */
    boolean pairedTTest(double[] sample1, double[] sample2, double alpha)
    throws IllegalArgumentException, MathException;
    
    /**
     * Computes a <a href="http://www.itl.nist.gov/div898/handbook/prc/section2/prc22.htm#formula"> 
     * t statistic </a> given observed values and a comparison constant.
     * <p>
     * This statistic can be used to perform a one sample t-test for the mean.
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The observed array length must be at least 2.
     * </li></ul>
     *
     * @param mu comparison constant
     * @param observed array of values
     * @return t statistic
     * @throws IllegalArgumentException if input array length is less than 2
     */
    double t(double mu, double[] observed) 
    throws IllegalArgumentException;
    
    /**
     * Computes a <a href="http://www.itl.nist.gov/div898/handbook/prc/section2/prc22.htm#formula">
     * t statistic </a> to use in comparing the mean of the dataset described by 
     * <code>sampleStats</code> to <code>mu</code>.
     * <p>
     * This statistic can be used to perform a one sample t-test for the mean.
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li><code>observed.getN() > = 2</code>.
     * </li></ul>
     *
     * @param mu comparison constant
     * @param sampleStats DescriptiveStatistics holding sample summary statitstics
     * @return t statistic
     * @throws IllegalArgumentException if the precondition is not met
     */
    double t(double mu, StatisticalSummary sampleStats) 
    throws IllegalArgumentException;
    
    /**
     * Computes a <a href="http://www.itl.nist.gov/div898/handbook/prc/section3/prc31.htm">
     * 2-sample t statistic. </a>
     * <p>
     * This statistic can be used to perform a two-sample t-test to compare
     * sample means.
     * <p>
     * If <code>equalVariances</code> is <code>true</code>,  the t-statisitc is
     * <p>
     * (1) &nbsp;&nbsp;<code>  t = (m1 - m2) / (sqrt(1/n1 +1/n2) sqrt(var))</code>
     * <p>
     * where <strong><code>n1</code></strong> is the size of first sample; 
     * <strong><code> n2</code></strong> is the size of second sample; 
     * <strong><code> m1</code></strong> is the mean of first sample;  
     * <strong><code> m2</code></strong> is the mean of second sample</li>
     * </ul>
     * and <strong><code>var</code></strong> is the pooled variance estimate:
     * <p>
     * <code>var = sqrt(((n1 - 1)var1 + (n2 - 1)var2) / ((n1-1) + (n2-1)))</code>
     * <p> 
     * with <strong><code>var1<code></strong> the variance of the first sample and
     * <strong><code>var2</code></strong> the variance of the second sample.
     * <p>
     * If <code>equalVariances</code> is <code>false</code>,  the t-statisitc is
     * <p>
     * (2) &nbsp;&nbsp; <code>  t = (m1 - m2) / sqrt(var1/n1 + var2/n2)</code>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The observed array lengths must both be at least 2.
     * </li></ul>
     *
     * @param sample1 array of sample data values
     * @param sample2 array of sample data values
     * @param equalVariances are the sample variances assumed equal?
     * @return t statistic
     * @throws IllegalArgumentException if the precondition is not met
     * @throws MathException if the statistic can not be computed do to a
     *         convergence or other numerical error.
     */
    double t(double[] sample1, double[] sample2, boolean equalVariances) 
    throws IllegalArgumentException, MathException;
    
    /**
     * Computes a <a href="http://www.itl.nist.gov/div898/handbook/prc/section3/prc31.htm">
     * 2-sample t statistic </a>, comparing the means of the datasets described
     * by two {@link StatisticalSummary} instances.
     * <p>
     * This statistic can be used to perform a two-sample t-test to compare
     * sample means.
     * <p>
      * If <code>equalVariances</code> is <code>true</code>,  the t-statisitc is
     * <p>
     * (1) &nbsp;&nbsp;<code>  t = (m1 - m2) / (sqrt(1/n1 +1/n2) sqrt(var))</code>
     * <p>
     * where <strong><code>n1</code></strong> is the size of first sample; 
     * <strong><code> n2</code></strong> is the size of second sample; 
     * <strong><code> m1</code></strong> is the mean of first sample;  
     * <strong><code> m2</code></strong> is the mean of second sample</li>
     * </ul>
     * and <strong><code>var</code></strong> is the pooled variance estimate:
     * <p>
     * <code>var = sqrt(((n1 - 1)var1 + (n2 - 1)var2) / ((n1-1) + (n2-1)))</code>
     * <p> 
     * with <strong><code>var1<code></strong> the variance of the first sample and
     * <strong><code>var2</code></strong> the variance of the second sample.
     * <p>
     * If <code>equalVariances</code> is <code>false</code>,  the t-statisitc is
     * <p>
     * (2) &nbsp;&nbsp; <code>  t = (m1 - m2) / sqrt(var1/n1 + var2/n2)</code>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The datasets described by the two Univariates must each contain
     * at least 2 observations.
     * </li></ul>
     *
     * @param sampleStats1 StatisticalSummary describing data from the first sample
     * @param sampleStats2 StatisticalSummary describing data from the second sample
     * @param equalVariances are the sample variances assumed equal?
     * @return t statistic
     * @throws IllegalArgumentException if the precondition is not met
     */
    double t(StatisticalSummary sampleStats1, StatisticalSummary sampleStats2,
            boolean equalVariances) 
    throws IllegalArgumentException;
    
    /**
     * Returns the <i>observed significance level</i>, or 
     * <a href="http://www.cas.lancs.ac.uk/glossary_v1.1/hyptest.html#pvalue">
     * p-value</a>, associated with a one-sample, two-tailed t-test 
     * comparing the mean of the input array with the constant <code>mu</code>.
     * <p>
     * The number returned is the smallest significance level
     * at which one can reject the null hypothesis that the mean equals 
     * <code>mu</code> in favor of the two-sided alternative that the mean
     * is different from <code>mu</code>. For a one-sided test, divide the 
     * returned value by 2.
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the test depends on the assumptions of the parametric
     * t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/ttest_unpaired_ass_viol.html">here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The observed array length must be at least 2.
     * </li></ul>
     *
     * @param mu constant value to compare sample mean against
     * @param sample array of sample data values
     * @return p-value
     * @throws IllegalArgumentException if the precondition is not met
     * @throws MathException if an error occurs computing the p-value
     */
    double tTest(double mu, double[] sample)
    throws IllegalArgumentException, MathException;
    
    /**
     * Performs a <a href="http://www.itl.nist.gov/div898/handbook/eda/section3/eda353.htm">
     * two-sided t-test</a> evaluating the null hypothesis that the mean of the population from
     *  which <code>sample</code> is drawn equals <code>mu</code>.
     * <p>
     * Returns <code>true</code> iff the null hypothesis can be 
     * rejected with confidence <code>1 - alpha</code>.  To 
     * perform a 1-sided test, use <code>alpha / 2</code>
     * <p>
     * <strong>Examples:</strong><br><ol>
     * <li>To test the (2-sided) hypothesis <code>sample mean = mu </code> at
     * the 95% level, use <br><code>tTest(mu, sample, 0.05) </code>
     * </li>
     * <li>To test the (one-sided) hypothesis <code> sample mean < mu </code>
     * at the 99% level, first verify that the measured sample mean is less 
     * than <code>mu</code> and then use 
     * <br><code>tTest(mu, sample, 0.005) </code>
     * </li></ol>
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the test depends on the assumptions of the one-sample 
     * parametric t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/sg_glos.html#one-sample">here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The observed array length must be at least 2.
     * </li></ul>
     *
     * @param mu constant value to compare sample mean against
     * @param sample array of sample data values
     * @param alpha significance level of the test
     * @return p-value
     * @throws IllegalArgumentException if the precondition is not met
     * @throws MathException if an error computing the p-value
     */
    boolean tTest(double mu, double[] sample, double alpha)
    throws IllegalArgumentException, MathException;
    
    /**
     * Returns the <i>observed significance level</i>, or 
     * <a href="http://www.cas.lancs.ac.uk/glossary_v1.1/hyptest.html#pvalue">
     * p-value</a>, associated with a one-sample, two-tailed t-test 
     * comparing the mean of the dataset described by <code>sampleStats</code>
     * with the constant <code>mu</code>.
     * <p>
     * The number returned is the smallest significance level
     * at which one can reject the null hypothesis that the mean equals 
     * <code>mu</code> in favor of the two-sided alternative that the mean
     * is different from <code>mu</code>. For a one-sided test, divide the 
     * returned value by 2.
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the test depends on the assumptions of the parametric
     * t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/ttest_unpaired_ass_viol.html">here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The sample must contain at least 2 observations.
     * </li></ul>
     *
     * @param mu constant value to compare sample mean against
     * @param sampleStats StatisticalSummary describing sample data
     * @return p-value
     * @throws IllegalArgumentException if the precondition is not met
     * @throws MathException if an error occurs computing the p-value
     */
    double tTest(double mu, StatisticalSummary sampleStats)
    throws IllegalArgumentException, MathException;
    
    /**
     * Performs a <a href="http://www.itl.nist.gov/div898/handbook/eda/section3/eda353.htm">
     * two-sided t-test</a> evaluating the null hypothesis that the mean of the population from
     * which the dataset described by <code>stats</code> is drawn equals <code>mu</code>.
     * <p>
     * Returns <code>true</code> iff the null hypothesis can be 
     * rejected with confidence <code>1 - alpha</code>.  To 
     * perform a 1-sided test, use <code>alpha / 2</code>
     * <p>
     * <strong>Examples:</strong><br><ol>
     * <li>To test the (2-sided) hypothesis <code>sample mean = mu </code> at
     * the 95% level, use <br><code>tTest(mu, sampleStats, 0.05) </code>
     * </li>
     * <li>To test the (one-sided) hypothesis <code> sample mean < mu </code>
     * at the 99% level, first verify that the measured sample mean is less 
     * than <code>mu</code> and then use 
     * <br><code>tTest(mu, sampleStats, 0.005) </code>
     * </li></ol>
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the test depends on the assumptions of the one-sample 
     * parametric t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/sg_glos.html#one-sample">here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The sample must include at least 2 observations.
     * </li></ul>
     *
     * @param mu constant value to compare sample mean against
     * @param sampleStats StatisticalSummary describing sample data values
     * @param alpha significance level of the test
     * @return p-value
     * @throws IllegalArgumentException if the precondition is not met
     * @throws MathException if an error occurs computing the p-value
     */
    boolean tTest(double mu, StatisticalSummary sampleStats, double alpha)
    throws IllegalArgumentException, MathException;
    
    /**
     * Returns the <i>observed significance level</i>, or 
     * <a href="http://www.cas.lancs.ac.uk/glossary_v1.1/hyptest.html#pvalue">
     * p-value</a>, associated with a two-sample, two-tailed t-test 
     * comparing the means of the input arrays.
     * <p>
     * The number returned is the smallest significance level
     * at which one can reject the null hypothesis that the two means are
     * equal in favor of the two-sided alternative that they are different. 
     * For a one-sided test, divide the returned value by 2.
     * <p>
     * If the <code>equalVariances</code> parameter is <code>false,</code>
     * the test does not assume that the underlying popuation variances are
     * equal  and it uses approximated degrees of freedom computed from the 
     * sample data to compute the p-value.  In this case, formula (1) for the
     * {@link #t(double[], double[], boolean)} statistic is used
     * and the Welch-Satterthwaite approximation to the degrees of freedom is used, 
     * as described 
     * <a href="http://www.itl.nist.gov/div898/handbook/prc/section3/prc31.htm">
     * here.</a>
     * <p>
     * If <code>equalVariances</code> is <code>true</code>, a pooled variance
     * estimate is used to compute the t-statistic (formula (2)) and the sum of the 
     * sample sizes minus 2 is used as the degrees of freedom.
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the p-value depends on the assumptions of the parametric
     * t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/ttest_unpaired_ass_viol.html">
     * here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The observed array lengths must both be at least 2.
     * </li></ul>
     *
     * @param sample1 array of sample data values
     * @param sample2 array of sample data values
     * @param equalVariances are sample variances assumed to be equal?
     * @return p-value for t-test
     * @throws IllegalArgumentException if the precondition is not met
     * @throws MathException if an error occurs computing the p-value
     */
    double tTest(double[] sample1, double[] sample2, boolean equalVariances)
    throws IllegalArgumentException, MathException;
    
    /**
     * Performs a <a href="http://www.itl.nist.gov/div898/handbook/eda/section3/eda353.htm">
     * two-sided t-test</a> evaluating the null hypothesis that <code>sample1</code> 
     * and <code>sample2</code> are drawn from populations with the same mean, 
     * with significance level <code>alpha</code>.
     * <p>
     * Returns <code>true</code> iff the null hypothesis that the means are
     * equal can be rejected with confidence <code>1 - alpha</code>.  To 
     * perform a 1-sided test, use <code>alpha / 2</code>
     * <p>
     * If the <code>equalVariances</code> parameter is <code>false,</code>
     * the test does not assume that the underlying popuation variances are
     * equal  and it uses approximated degrees of freedom computed from the 
     * sample data to compute the p-value.  In this case, formula (1) for the
     * {@link #t(double[], double[], boolean)} statistic is used
     * and the Welch-Satterthwaite approximation to the degrees of freedom is used, 
     * as described 
     * <a href="http://www.itl.nist.gov/div898/handbook/prc/section3/prc31.htm">
     * here.</a>
     * <p>
     * If <code>equalVariances</code> is <code>true</code>, a pooled variance
     * estimate is used to compute the t-statistic (formula (2)) and the sum of the 
     * sample sizes minus 2 is used as the degrees of freedom.
     * <p>
     * <strong>Examples:</strong><br><ol>
     * <li>To test the (2-sided) hypothesis <code>mean 1 = mean 2 </code> at
     * the 95% level, under the assumption of equal subpopulation variances, 
     * use <br><code>tTest(sample1, sample2, 0.05, true) </code>
     * </li>
     * <li>To test the (one-sided) hypothesis <code> mean 1 < mean 2 </code>
     * at the 99% level without assuming equal variances, first verify that the measured 
     * mean of <code>sample 1</code> is less than the mean of <code>sample 2</code>
     * and then use <br><code>tTest(sample1, sample2, 0.005, false) </code>
     * </li></ol>
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the test depends on the assumptions of the parametric
     * t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/ttest_unpaired_ass_viol.html">
     * here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The observed array lengths must both be at least 2.
     * </li>
     * <li> <code> 0 < alpha < 0.5 </code>
     * </li></ul>
     *
     * @param sample1 array of sample data values
     * @param sample2 array of sample data values
     * @param alpha significance level of the test
     * @param equalVariances are sample variances assumed to be equal?
     * @return true if the null hypothesis can be rejected with 
     * confidence 1 - alpha
     * @throws IllegalArgumentException if the preconditions are not met
     * @throws MathException if an error occurs performing the test
     */
    boolean tTest(double[] sample1, double[] sample2, double alpha, 
            boolean equalVariances)
    throws IllegalArgumentException, MathException;
    
    /**
     * Returns the <i>observed significance level</i>, or 
     * <a href="http://www.cas.lancs.ac.uk/glossary_v1.1/hyptest.html#pvalue">
     * p-value</a>, associated with a two-sample, two-tailed t-test 
     * comparing the means of the datasets described by two Univariates.
     * <p>
     * The number returned is the smallest significance level
     * at which one can reject the null hypothesis that the two means are
     * equal in favor of the two-sided alternative that they are different. 
     * For a one-sided test, divide the returned value by 2.
     * <p>
     * If the <code>equalVariances</code> parameter is <code>false,</code>
     * the test does not assume that the underlying popuation variances are
     * equal  and it uses approximated degrees of freedom computed from the 
     * sample data to compute the p-value.  In this case, formula (1) for the
     * {@link #t(double[], double[], boolean)} statistic is used
     * and the Welch-Satterthwaite approximation to the degrees of freedom is used, 
     * as described 
     * <a href="http://www.itl.nist.gov/div898/handbook/prc/section3/prc31.htm">
     * here.</a>
     * <p>
     * If <code>equalVariances</code> is <code>true</code>, a pooled variance
     * estimate is used to compute the t-statistic (formula (2)) and the sum of the 
     * sample sizes minus 2 is used as the degrees of freedom.
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the p-value depends on the assumptions of the parametric
     * t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/ttest_unpaired_ass_viol.html">here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The datasets described by the two Univariates must each contain
     * at least 2 observations.
     * </li></ul>
     *
     * @param sampleStats1  StatisticalSummary describing data from the first sample
     * @param sampleStats2  StatisticalSummary describing data from the second sample
     * @param equalVariances  are sample variances assumed to be equal?
     * @return p-value for t-test
     * @throws IllegalArgumentException if the precondition is not met
     * @throws MathException if an error occurs computing the p-value
     */
    double tTest(StatisticalSummary sampleStats1, StatisticalSummary sampleStats2, 
            boolean equalVariances)
    throws IllegalArgumentException, MathException;
    
    /**
     * Performs a <a href="http://www.itl.nist.gov/div898/handbook/eda/section3/eda353.htm">
     * two-sided t-test</a> evaluating the null hypothesis that <code>sampleStats1</code>
     * and <code>sampleStats2</code> describe datasets drawn from populations with the 
     * same mean, with significance level <code>alpha</code>.
     * <p>
     * Returns <code>true</code> iff the null hypothesis that the means are
     * equal can be rejected with confidence <code>1 - alpha</code>.  To 
     * perform a 1-sided test, use <code>alpha / 2</code>
     * <p>
     * If the <code>equalVariances</code> parameter is <code>false,</code>
     * the test does not assume that the underlying popuation variances are
     * equal  and it uses approximated degrees of freedom computed from the 
     * sample data to compute the p-value.  In this case, formula (1) for the
     * {@link #t(double[], double[], boolean)} statistic is used
     * and the Welch-Satterthwaite approximation to the degrees of freedom is used, 
     * as described 
     * <a href="http://www.itl.nist.gov/div898/handbook/prc/section3/prc31.htm">
     * here.</a>
     * <p>
     * If <code>equalVariances</code> is <code>true</code>, a pooled variance
     * estimate is used to compute the t-statistic (formula (2)) and the sum of the 
     * sample sizes minus 2 is used as the degrees of freedom.
     * <p>
     * <strong>Examples:</strong><br><ol>
     * <li>To test the (2-sided) hypothesis <code>mean 1 = mean 2 </code> at
     * the 95% level under the assumption of equal subpopulation variances, use 
     * <br><code>tTest(sampleStats1, sampleStats2, 0.05, true) </code>
     * </li>
     * <li>To test the (one-sided) hypothesis <code> mean 1 < mean 2 </code>
     * at the 99% level without assuming that subpopulation variances are equal, 
     * first verify that the measured mean of  <code>sample 1</code> is less than 
     * the mean of <code>sample 2</code> and then use 
     * <br><code>tTest(sampleStats1, sampleStats2, 0.005, false) </code>
     * </li></ol>
     * <p>
     * <strong>Usage Note:</strong><br>
     * The validity of the test depends on the assumptions of the parametric
     * t-test procedure, as discussed 
     * <a href="http://www.basic.nwu.edu/statguidefiles/ttest_unpaired_ass_viol.html">
     * here</a>
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>The datasets described by the two Univariates must each contain
     * at least 2 observations.
     * </li>
     * <li> <code> 0 < alpha < 0.5 </code>
     * </li></ul>
     *
     * @param sampleStats1 StatisticalSummary describing sample data values
     * @param sampleStats2 StatisticalSummary describing sample data values
     * @param alpha significance level of the test
     * @param equalVariances  are sample variances assumed to be equal?
     * @return true if the null hypothesis can be rejected with 
     * confidence 1 - alpha
     * @throws IllegalArgumentException if the preconditions are not met
     * @throws MathException if an error occurs performing the test
     */
    boolean tTest(StatisticalSummary sampleStats1, StatisticalSummary sampleStats2, 
            double alpha, boolean equalVariances)
    throws IllegalArgumentException, MathException;
}
