package org.app.gitReader.GitReader.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GitResponseData {

    public String fileName;
    public List<String[]> data;
}
