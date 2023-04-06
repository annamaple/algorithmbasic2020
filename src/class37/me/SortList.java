package class37.me;

// 有序表, 不去重, 记录数的个数
public interface SortList<K extends Comparable<K>> {

    // 查询k在[a, b]区间内的个数
    default int between(K a, K b) {
        return smallerAndEquals(b) - smaller(a);
    }

    // 小于num的个数
    int smaller(K k);

    // 小于等于num的个数
    int smallerAndEquals(K k);

    // 添加
    void put(K k);
}