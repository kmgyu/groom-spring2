package goorm.mybatisboard.product.exception;

/**
 * Excel 파일 생성 실패 예외
 */
public class ExcelCreationException extends ExcelExportException {

    public ExcelCreationException(String message, Throwable cause) {
        super("EXCEL_CREATION_FAILED", message, cause);
    }

    public ExcelCreationException(String message) {
        super("EXCEL_CREATION_FAILED", message);
    }
}