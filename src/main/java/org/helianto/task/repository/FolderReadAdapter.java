package org.helianto.task.repository;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Adaptor to folders.
 * 
 * @author mauriciofernandesdecastro
 */
public class FolderReadAdapter 
	extends AbstractReadAdapter
{

	private static final long serialVersionUID = 1L;
	
	protected int categoryId;
	
	protected String folderCode = "";
	
	protected String folderName = "";
	
	protected String folderDecorationUrl = "";

	protected String numberPrefix = "";
	
	protected int numberSize = 3;
	
	protected String numberSuffix = "";
	
	protected char contentType;
    
	protected char activityState;
	
	protected Date referenceDate;
	
	protected Integer exporterId = 0;
    
    /**
     * Default constructor.
     */
	public FolderReadAdapter() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param categoryId
	 * @param folderCode
	 * @param folderName
	 * @param folderDecorationUrl
	 * @param contentType
	 */
	public FolderReadAdapter(int id, int categoryId, String folderCode,
			String folderName, String folderDecorationUrl, char contentType) {
		super(id);
		this.categoryId = categoryId;
		this.folderCode = folderCode;
		this.folderName = folderName;
		this.folderDecorationUrl = folderDecorationUrl;
		this.contentType = contentType;
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param categoryId
	 * @param folderCode
	 * @param folderName
	 * @param folderDecorationUrl
	 * @param contentType
	 * @param numberPrefix
	 * @param numberSize (max 6)
	 * @param activityState
	 */
	public FolderReadAdapter(int id, int categoryId, String folderCode,
			String folderName, String folderDecorationUrl, char contentType,
			String numberPrefix, int numberSize, char activityState) {
		this(id, categoryId, folderCode, folderName, folderDecorationUrl, contentType);
		this.numberPrefix = numberPrefix;
		if (numberSize>6) {
			numberSize = 6;
		}
		this.numberSize = numberSize;
		this.activityState = activityState;
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param categoryId
	 * @param folderCode
	 * @param folderName
	 * @param folderDecorationUrl
	 * @param contentType
	 * @param numberPrefix
	 * @param numberSuffix
	 * @param numberSize
	 */
	public FolderReadAdapter(int id, int categoryId, String folderCode, String folderName, 
			String folderDecorationUrl, char contentType, 
			String numberPrefix, String numberSuffix,
			int numberSize) {
		this(id, categoryId, folderCode, folderName, folderDecorationUrl, contentType);
		this.numberPrefix = numberPrefix;
		this.numberSuffix = numberSuffix;
		this.numberSize = numberSize;
	}

	/**
	 * Constructor without contentType
	 * 
	 * @param id
	 * @param categoryId
	 * @param folderCode
	 * @param folderName
	 * @param folderDecorationUrl
	 * @param numberPrefix
	 * @param numberSuffix
	 * @param numberSize
	 */
	public FolderReadAdapter(int id, int categoryId, String folderCode, String folderName, 
			String folderDecorationUrl, 
			String numberPrefix, String numberSuffix,
			int numberSize) {
		this(id, categoryId, folderCode, folderName, folderDecorationUrl, '_');
		this.numberPrefix = numberPrefix;
		this.numberSuffix = numberSuffix;
		this.numberSize = numberSize;
	}
	
	/**
	 * Constructor exported
	 * 
	 * @param id
	 * @param categoryId
	 * @param folderCode
	 * @param folderName
	 * @param folderDecorationUrl
	 * @param numberPrefix
	 * @param numberSuffix
	 * @param numberSize
	 * @param exporterId
	 */
	public FolderReadAdapter(int id, int categoryId, String folderCode, String folderName, 
			String folderDecorationUrl, 
			String numberPrefix, String numberSuffix,
			int numberSize, Integer exporterId) {
		this(id, categoryId, folderCode, folderName, folderDecorationUrl, '_');
		this.numberPrefix = numberPrefix;
		this.numberSuffix = numberSuffix;
		this.numberSize = numberSize;
		this.exporterId = exporterId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getFolderCode() {
		return folderCode;
	}

	public String getFolderName() {
		return folderName;
	}

	public String getFolderDecorationUrl() {
		return folderDecorationUrl;
	}
	
	public String getNumberPrefix() {
		return numberPrefix;
	}
	
	public int getNumberSize() {
		return numberSize;
	}
	
	public String getNumberSuffix() {
		return getInternalNumberSuffix(numberSuffix);
	}
	
	public Integer getExporterId() {
		return exporterId;
	}
	
	/**
	 * Customize suffix.
	 * 
	 * @param numberSuffix
	 */
	protected String getInternalNumberSuffix(String numberSuffix) {
		if (getReferenceDate()!=null) {
			return getInternalNumberSuffix(numberSuffix, getReferenceDate());
		}
		return numberSuffix;
	}
	
	/**
	 * To customize suffix.
	 * 
	 * @param numberSuffix
	 * @param referenceDate
	 */
	protected String getInternalNumberSuffix(String numberSuffix, Date referenceDate) {
		numberSuffix = new SimpleDateFormat("'/'yyyy").format(referenceDate);
		return numberSuffix;
	}
	
	public char getContentType() {
		return contentType;
	}
	
	public char getActivityState() {
		return activityState;
	}
	
	/**
	 * Reference date used by suffix.
	 * @return
	 */
	public Date getReferenceDate() {
		return referenceDate;
	}
	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	/**
	 * Create 'numberPattern'.
	 */
	@JsonIgnore
	public String getNumberPattern() {
		if (getNumberPrefix()!=null && !getNumberPrefix().isEmpty()) {
			if (getNumberPrefix().startsWith("'")) {
				// significa que numberPrefix foi usado para 
				// guardar numberPattern (o formato antigo)
				// neste caso, simplesmente devolvemos numberPrefix
				return getNumberPrefix();
			}
			// caso contrário
			// construímos numberPattern com numberPrefix e numberSize.
			StringBuilder builder = new StringBuilder("'")
			.append(getNumberPrefix())
			.append("'")
			.append(String.format("%0"+getNumberSize()+"d", 0));
			if (getNumberSuffix()!=null && !getNumberSuffix().isEmpty()) {
				builder.append("'")
				.append(getNumberSuffix())
				.append("'");
			}
			return builder.toString(); 
		}
		return "";
	}
	
	public String generateCode(String pattern, Long internalNumber) {
		return new DecimalFormat(pattern).format(internalNumber);
	}
	
}
