package Modules.CommonModels.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum FileStatus {

	UPDATED("UPDATED"),
	INCOMPLETE("INCOMPLETE"),
	NOT_FOUND("NOT_FOUND"),
	IN_PROGRESS("IN_PROCESS");


    FileStatus(String notFound) {
    }
}
