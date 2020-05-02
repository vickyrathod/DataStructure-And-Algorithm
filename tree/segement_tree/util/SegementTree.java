package org.example.segmenttree.util;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SegementTree<T, Type> {
    private ArrayList<T> seg;
    private int size;
    Function<Type,T> getSegmentTreeValue;
    BiFunction<T, T, T> joinValueOfSegment;

    /*
     * Build segment tree accept getSegmentTreeValue function for converting arrayValue to segmentTree Value and
     * joinValueOfSegment function for joining two value of segment tree.
     * joinValueOfSegment is main operation of segement tree.
     */
    public void bulidSegementTree(int index, int start, int end, Type[] arr) {
        if(start == end){
            seg.add(index, getSegmentTreeValue.apply(arr[start]));
            return;
        }
        int mid = (start + end) / 2;
        bulidSegementTree( 2 * index + 1, start, mid, arr);
        bulidSegementTree( 2 * index + 2, mid + 1, end, arr);

        seg.add(index, joinValueOfSegment.apply(seg.get(2 * index + 1), seg.get(2 * index + 2)));
    }

    public T executeQuery(int index, int start, int end, int queryStart, int queryEnd){
            if(start >= queryEnd && end <= queryStart)
                return seg.get(index);
            int mid = (start + end) / 2;
            if(queryStart >= start && queryEnd <= mid)
                return executeQuery(2 * index + 1, start, mid, queryStart, queryEnd);
            if(queryStart > mid && queryEnd <= end)
                return executeQuery(2 * index + 2, mid + 1, end, queryStart, queryEnd);

            return joinValueOfSegment.apply(executeQuery(2 * index + 1, start, mid, queryStart, mid),
                    executeQuery(2 * index + 2, mid + 1, end, mid + 1, queryEnd));
    }

    //Constructor of segment Tree
    public SegementTree(int size, Function<Type,T> getSegmentTreeValue, BiFunction<T, T, T> joinValueOfSegment){
        this.size = size;
        seg = new ArrayList<T>(size);
        this.getSegmentTreeValue = getSegmentTreeValue;
        this.joinValueOfSegment = joinValueOfSegment;
    }

    public void addValueAt(int i, T t){
        if(validateIndex(i));
            seg.add(i, t);
    }

    private boolean validateIndex(int i) {
        if(i > 2 * size || i < 0)
            return false;
        return true;
    }
}
