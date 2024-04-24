package com.kosa.tikitaka.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("docPage")
@NoArgsConstructor
@AllArgsConstructor
public class DocPageDTO {
	private int docPageNo;
	private String docTitle;
	private String docContent;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date updateDate;
	private int projNo;
}
