package com.example.guoyurenli.util;

import java.io.OutputStream;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRcode
{
	
	/** 生成二维码 : 参考《项目应用篇》之 二维码 相关章节
	 * 
	 * @param content 要嵌入的内容文本，通常是一个URL
	 * @param format  图片格式, "jpeg" 或 "png"
	 * @param outputStream 输出数据
	 */
	public static void gen(int width, int height
			, String content
			, String format
			, OutputStream outputStream) throws Exception
	{
		// 二维码的参数设置
		HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");// 字符集
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 纠错级别
		hints.put(EncodeHintType.MARGIN, 2);// 空白
		//hints.put(EncodeHintType.QR_VERSION, 5);// 版本1-40，内部会自动适应，不用设置
		
		// 以下三行可能抛异常
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		MatrixToImageWriter.writeToStream(matrix, format, outputStream);

	}
}
