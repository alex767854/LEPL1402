package algorithms;

public abstract class DecisionTree {

    /**
     * Predicts the label of the input "features" in this decision tree
     * The prediction is done by following a path from the root to a leaf node
     * which contains the predicted label.
     * The path followed is fixed by the values of a boolean feature array.
     * Each node contains the index of a feature to test.
     * If the feature at that index is true, the left child is followed,
     * otherwise the right one is followed.
     *
     * Consider the following decision tree:
     *
     *                4
     *               / \
     *              2   T
     *             / \
     *            F   T
     *
     * [F,T,F,F,T] would receive the prediction label T and the path followed would be:
     *                4
     *               /
     *              2
     *               \
     *                T
     *
     * [F,T,T,F,F] would receive the prediction label F and the path followed would be:
     *                 4
     *                  \
     *                   T
     *
     * @param features the boolean input features that are used to determine the path to follow
     *
     * @return the boolean label of the leaf node reached by following the path from the
     *         root to the leaf determined by the value of the features and the tests of the
     *         decision tree.
     */
    public abstract boolean predict(boolean [] features);

    /**
     * Merge two decision trees such that the resulting prediction is operated as follows:
     * 1) the feature at featureIndex is tested,
     * 2) if it is true the predict decision is delegated to the left subtree,
     *    otherwise it is delegated to the right subtree
     * @param featureIndex the feature to be tested
     * @param left the left subtree
     * @param right the right subtree
     * @return a new decision tree such that the left tree is used for the prediction if
     *         the feature at featureIndex is true, the right tree is used otherwise
     */
    public static DecisionTree splitNode(int featureIndex, DecisionTree left, DecisionTree right) {

        DecisionTree result = new mini(featureIndex,left,right);
        return result;

    }

    /**
     * Return a decision tree that always predicts label
     * @param label the label to be predicted
     * @return a decision tree that always predicts label
     */
    public static DecisionTree decisionNode(boolean label) {
        return new leaf(label);
    }

    public static class leaf extends DecisionTree {
        public boolean label;

        public leaf (boolean label){
            this.label = label;
        }

        @Override
        public boolean predict(boolean [] features){
            return label;
        }
    }
    public static class mini extends DecisionTree {
        public int value;
        public DecisionTree left;
        public DecisionTree right;

        public mini (int value, DecisionTree left, DecisionTree right){
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean predict(boolean [] features){
            if (features[value]){
                return left.predict(features);
            }
            else{
                return right.predict(features);
            }
        }
    }

}
