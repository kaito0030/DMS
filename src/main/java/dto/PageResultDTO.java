package dto;

import java.util.List;

public class PageResultDTO<T> {

    private List<T> list;
    private int totalCount;

    public PageResultDTO(List<T> list, int totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public int getTotalCount() {
        return totalCount;
    }
}