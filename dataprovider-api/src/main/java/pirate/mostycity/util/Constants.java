package pirate.mostycity.util;

public interface Constants {
	
	public static final Long ACCOUNT_STATUS_ACTIVE = 1l;
	public static final Long ACCOUNT_STATUS_DELETED = 2l;
	public static final Long ACCOUNT_STATUS_INACTIVE = 3l;

	public static final Long ACCOUNT_TYPE_GUEST = 1l;
	public static final Long ACCOUNT_TYPE_USER = 2l;
	public static final Long ACCOUNT_TYPE_MODERATOR = 3l;
	public static final Long ACCOUNT_TYPE_ADMIN = 4l;
	public static final Long ACCOUNT_TYPE_SUPERADMIN = 5l;
	
	public static final Long ACCOUNT_GUEST_ID = 1l;
	
	public static final Long NEWS_ITEM_STATUS_ACTIVE = 1l;
	public static final Long NEWS_ITEM_STATUS_DELETED = 2l;
	public static final Long NEWS_ITEM_STATUS_ARCHIVED = 3l;
	
	public static final int MESSAGE_TYPE_ALL = 1;
	public static final int MESSAGE_TYPE_NEW = 2;
	public static final int MESSAGE_TYPE_INCOMMING = 3;
	public static final int MESSAGE_TYPE_SENT = 4;
	
	public static final int MESSAGES_ROWS_PER_PAGE = 10;
	public static final int COMMENTS_MAX_RESULT = 3;
	public static final int NEWS_MAX_RESULT = 5;
	public static final int MAIN_NEWS_MAX_RESULT = 5;
	public static final int PHOTOS_PER_PAGE = 30;
	
	public static final boolean IS_UPDATE = true;
	public static final boolean IS_NOT_UPDATE = false;
	
	public static final int MAX_LENGTH = 220;
	public static final int NEWS_ITEM_DESC_MAX_LENGTH = 750;
	
	// parameters constants
	public static final String NEWS_ITEM_ID = "newsItemId";
	public static final String ACCOUNT_ID = "accountId";
	public static final String TO_ACCOUNT_ID = "toAccountId";
	public static final String COMMENT_ITEM_ID = "commentItemId";
	public static final String MESSAGE_ID = "messageId";
	public static final String MESSAGE_TYPE = "messageType";
	public static final String INTERLOCUTER_ID= "interlocuterId";
	public static final String VOTING_ID= "votingId";
	public static final String VOTING_VARIANT_ID= "votingVariantId";
	public static final String IS_COMMENTS_VISIBLE= "isCommentsVisible";
	
	// PATH
	public static final String FULL_PATH_TO_GALLERY_FOLDER = "webapps/mostycity/images/left_panel";
	public static final String SHORT_PATH_TO_GALLERY_FOLDER = "images/left_panel/";
	public static final String FULL_PATH_TO_SMILES_FOLDER = "webapps/mostycity/images/smiles";
	public static final String SHORT_PATH_TO_SMILES_FOLDER = "images/smiles/";
}
