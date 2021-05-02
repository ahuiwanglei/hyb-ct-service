package com.mszx.hyb.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageInfoResult<T> implements Serializable {
    private long total;
    private int pages;
    private List<T> list;

}
