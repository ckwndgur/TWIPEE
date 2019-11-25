package com.nslb.twipee.communication;

import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class GUIInterface_Sender {

	private DataPacketStore.LoginSubPacket lLoginSubPacket = new DataPacketStore.LoginSubPacket();
	private DataPacketStore.TripTalkSubPacket tTripTalkSubPacket = new DataPacketStore.TripTalkSubPacket();
	private DataPacketStore.MainPacket mMainPacket = new DataPacketStore.MainPacket();
	private DataPacketStore.PostSubPacket pPostSubPacket = new DataPacketStore.PostSubPacket();
	private DataPacketStore.PageSubPacket pPageSubPacket = new DataPacketStore.PageSubPacket();
	private DataPacketStore.FollowSubPacket fFollowSubPacket = new DataPacketStore.FollowSubPacket();
	private DataPacketStore.header Header1 = new DataPacketStore.header();
	private DefineValue DEFINE = new DefineValue();

	private int MainSize;
	private int SubSize;

	private byte[] HeaderByte = new byte[DEFINE.HEADER_SIZE];
	private byte[] TotalyPacket = new byte[800];

	private Receiver Sender = new Receiver();

	private byte[] integersToBytes1(int[] values) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		for (int i = 0; i < values.length; ++i) {
			dos.writeInt(values[i]);
		}
		return baos.toByteArray();
	}

	//----------------------==---------------------------------------------------------------------------------//
	private void MainPacketClear() {
		mMainPacket.PacketSize = 0;
		mMainPacket.DataType = 0;
		mMainPacket.UserID = null;
		mMainPacket.Time = null;
	}
	//--------------------------------------------�α׸����� Sender-----------------------------------------//

	private void LoginPacketClear() {
		lLoginSubPacket.SubType = 0;
		lLoginSubPacket.UserPW = null;
		lLoginSubPacket.UserName = null;
		lLoginSubPacket.UserEmail = null;
		lLoginSubPacket.PhoneNumber = null;
		lLoginSubPacket.BirthDay = null;
		lLoginSubPacket.SearchedID = null;
		lLoginSubPacket.SearchedPW = null;
		lLoginSubPacket.SearchedFlag = false;
		lLoginSubPacket.LoginSuccessFlag = false;
		lLoginSubPacket.LogoutSuccessFlag = false;
		lLoginSubPacket.IDDuplicateFlag = false;
		lLoginSubPacket.JoinSuccessFlag = false;
		lLoginSubPacket.EditFlag = false;
	}

	private void PagePacketClear() {
		pPageSubPacket.ChatID = null;
		pPageSubPacket.ClickedID = null;
		pPageSubPacket.EditFlag = false;
		pPageSubPacket.PersonalMessage = null;
		pPageSubPacket.PostData = null;
		pPageSubPacket.ProfileData = null;
		pPageSubPacket.ProfileInfo = null;
		pPageSubPacket.Step = 0;
		pPageSubPacket.SubType = 0;

	}

	private void FollowPacketClear() {
		fFollowSubPacket.SubType = 0;
		fFollowSubPacket.FollowID = null;
		fFollowSubPacket.FollowCheck = false;
	}

	private void PostPacketClear() {
		pPostSubPacket.DeleteFlag = false;
		pPostSubPacket.EditFlag = false;
		pPostSubPacket.PostData = null;
		pPostSubPacket.PostNumber = 0;
		pPostSubPacket.PublishFlag = false;
		pPostSubPacket.Reply = null;
		pPostSubPacket.SubType = 0;
		pPostSubPacket.TripStatus = false;
	}

	private void TripTalkPacketClear() {
		tTripTalkSubPacket.BoardData = null;
		tTripTalkSubPacket.BoardNumber = 0;
		tTripTalkSubPacket.BoardPublishFlag = false;
		tTripTalkSubPacket.ChatID = null;
		tTripTalkSubPacket.ChatImageBuffer = null;
		tTripTalkSubPacket.ChatMessage = null;
		tTripTalkSubPacket.ChatRoomTitle = null;
		tTripTalkSubPacket.DeleteFlag = false;
		tTripTalkSubPacket.EditFlag = false;
		tTripTalkSubPacket.InsertFlag = false;
		tTripTalkSubPacket.InsertLeaveID = null;
		tTripTalkSubPacket.Participants = null;
		tTripTalkSubPacket.PublishSuccessFlag = false;
		tTripTalkSubPacket.Reply = null;
		tTripTalkSubPacket.SubType = 0;
		tTripTalkSubPacket.TlaverDistribution = null;
		tTripTalkSubPacket.TlaverDistributionApply = false;
	}

	private void Serialize_Login() throws IOException {
		byte[] MainByte = SerializationUtils.serialize((Serializable) mMainPacket);
		MainSize = MainByte.length;
		Header1.header[0] = MainSize;

		byte[] SubByte = SerializationUtils.serialize((Serializable) lLoginSubPacket);
		SubSize = SubByte.length;
		Header1.header[1] = SubSize;

		HeaderByte = integersToBytes1(Header1.header);
		System.arraycopy(HeaderByte, 0, TotalyPacket, 0, HeaderByte.length);
		System.arraycopy(MainByte, 0, TotalyPacket, HeaderByte.length, MainByte.length);
		System.arraycopy(SubByte, 0, TotalyPacket, MainByte.length + HeaderByte.length, SubByte.length);
	}

	private void Serialize_TripTalk() throws IOException {
		byte[] MainByte = SerializationUtils.serialize((Serializable) mMainPacket);
		MainSize = MainByte.length;
		Header1.header[0] = MainSize;

		byte[] SubByte = SerializationUtils.serialize((Serializable) tTripTalkSubPacket);
		SubSize = SubByte.length;
		Header1.header[1] = SubSize;

		HeaderByte = integersToBytes1(Header1.header);
		System.arraycopy(HeaderByte, 0, TotalyPacket, 0, HeaderByte.length);
		System.arraycopy(MainByte, 0, TotalyPacket, HeaderByte.length, MainByte.length);
		System.arraycopy(SubByte, 0, TotalyPacket, MainByte.length + HeaderByte.length, SubByte.length);
	}

	private void Serialize_Page() throws IOException {
		byte[] MainByte = SerializationUtils.serialize((Serializable) mMainPacket);
		MainSize = MainByte.length;
		Header1.header[0] = MainSize;

		byte[] SubByte = SerializationUtils.serialize((Serializable) pPageSubPacket);
		SubSize = SubByte.length;
		Header1.header[1] = SubSize;

		HeaderByte = integersToBytes1(Header1.header);
		System.arraycopy(HeaderByte, 0, TotalyPacket, 0, HeaderByte.length);
		System.arraycopy(MainByte, 0, TotalyPacket, HeaderByte.length, MainByte.length);
		System.arraycopy(SubByte, 0, TotalyPacket, MainByte.length + HeaderByte.length, SubByte.length);
	}

	private void Serialize_Follow() throws IOException {
		byte[] MainByte = SerializationUtils.serialize((Serializable) mMainPacket);
		MainSize = MainByte.length;
		Header1.header[0] = MainSize;

		byte[] SubByte = SerializationUtils.serialize((Serializable) fFollowSubPacket);
		SubSize = SubByte.length;
		Header1.header[1] = SubSize;

		HeaderByte = integersToBytes1(Header1.header);
		System.arraycopy(HeaderByte, 0, TotalyPacket, 0, HeaderByte.length);
		System.arraycopy(MainByte, 0, TotalyPacket, HeaderByte.length, MainByte.length);
		System.arraycopy(SubByte, 0, TotalyPacket, MainByte.length + HeaderByte.length, SubByte.length);
	}

	private void Serialize_post() throws IOException {

		byte[] MainByte = SerializationUtils.serialize((Serializable) mMainPacket);
		MainSize = MainByte.length;
		Header1.header[0] = MainSize;

		byte[] SubByte = SerializationUtils.serialize((Serializable) pPostSubPacket);
		SubSize = SubByte.length;
		Header1.header[1] = SubSize;

		HeaderByte = integersToBytes1(Header1.header);
		System.arraycopy(HeaderByte, 0, TotalyPacket, 0, HeaderByte.length);
		System.arraycopy(MainByte, 0, TotalyPacket, HeaderByte.length, MainByte.length);
		System.arraycopy(SubByte, 0, TotalyPacket, MainByte.length + HeaderByte.length, SubByte.length);
	}

	public void Reconnect_Sender(String UserID) throws IOException {
		MainPacketClear();
		LoginPacketClear();
		mMainPacket.DataType = DEFINE.LOGIN_PACKET;
		mMainPacket.UserID = UserID;
		lLoginSubPacket.SubType = DEFINE.RECONNECT;

		Serialize_Login();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void Login_Sender(String UserID, String UserPW) throws IOException {
		MainPacketClear();
		LoginPacketClear();
		mMainPacket.DataType = DEFINE.LOGIN_PACKET;
		mMainPacket.UserID = UserID;
		lLoginSubPacket.SubType = DEFINE.LOGIN_SUCCESS;
		lLoginSubPacket.UserPW = UserPW;

		Serialize_Login();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void Logout_Sender(String UserID) throws IOException {
		MainPacketClear();
		LoginPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.LOGIN_PACKET;
		lLoginSubPacket.SubType = DEFINE.LOGOUT_SUCCESS;

		Serialize_Login();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();	}

	public void Join_Sender(String UserID, String UserName, String UserPW, String Birthday, String PhoneNumber) throws IOException {
		MainPacketClear();
		LoginPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.LOGIN_PACKET;
		lLoginSubPacket.SubType = DEFINE.JOIN_SUCCESS;
		lLoginSubPacket.UserName = UserName;
		lLoginSubPacket.UserPW = UserPW;
		lLoginSubPacket.BirthDay = Birthday;
		lLoginSubPacket.PhoneNumber = PhoneNumber;

		Serialize_Login();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void IDDuplicate_Sender(String SearchedID) throws IOException {
		MainPacketClear();
		LoginPacketClear();
		mMainPacket.DataType = DEFINE.LOGIN_PACKET;
		lLoginSubPacket.SubType = DEFINE.ID_DUPLICATE_CHECK;
		lLoginSubPacket.SearchedID = SearchedID;

		Serialize_Login();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void IDFind_Sender(String UserName, String Birthday, String PhoneNumber) throws IOException {
		MainPacketClear();
		LoginPacketClear();
		mMainPacket.DataType = DEFINE.LOGIN_PACKET;
		lLoginSubPacket.SubType = DEFINE.ID_SEARCHED;
		lLoginSubPacket.UserName = UserName;
		lLoginSubPacket.BirthDay = Birthday;
		lLoginSubPacket.PhoneNumber = PhoneNumber;

		Serialize_Login();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PWFind_Sender(String UserID, String UserName, String Birthday, String PhoneNumber) throws IOException {
		MainPacketClear();
		LoginPacketClear();
		mMainPacket.DataType = DEFINE.LOGIN_PACKET;
		mMainPacket.UserID = UserID;
		lLoginSubPacket.SubType = DEFINE.PW_SEARCHED;
		lLoginSubPacket.UserName = UserName;
		lLoginSubPacket.BirthDay = Birthday;
		lLoginSubPacket.PhoneNumber = PhoneNumber;

		Serialize_Login();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void UserInfoEdit_Sender(String UserID, String UserPW, String UserName, String UserEmail, String PhoneNumber, String BirthDay) throws IOException {
		MainPacketClear();
		LoginPacketClear();
		mMainPacket.DataType = DEFINE.LOGIN_PACKET;
		mMainPacket.UserID = UserID;
		lLoginSubPacket.SubType = DEFINE.USER_IMFORMATION_EDIT;
		lLoginSubPacket.UserPW = UserPW;
		lLoginSubPacket.UserName = UserName;
		lLoginSubPacket.UserEmail = UserEmail;
		lLoginSubPacket.PhoneNumber = PhoneNumber;
		lLoginSubPacket.BirthDay = BirthDay;

		Serialize_Login();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	//---------------------Ʈ����-------����-----------------------------//
	public void TripTalkChatInsert_Sender(String UserID, String ChatTitle) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		mMainPacket.UserID = UserID;
		tTripTalkSubPacket.SubType = DEFINE.PARTICIPATE_NOTICE;
		tTripTalkSubPacket.ChatRoomTitle = ChatTitle;
		tTripTalkSubPacket.InsertFlag = true;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void TripTalkChatLeave_Sender(String UserID, String ChatTitle, boolean InserLeavetFlag) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		mMainPacket.UserID = UserID;
		tTripTalkSubPacket.SubType = DEFINE.LEAVE_PARTICIPATE_NOTICE;
		tTripTalkSubPacket.ChatRoomTitle = ChatTitle;
		tTripTalkSubPacket.InsertFlag = InserLeavetFlag;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void TripTalkChatMessage_Sender(String UserID, String ChatTitle, String Message) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		mMainPacket.UserID = UserID;
		tTripTalkSubPacket.SubType = DEFINE.TRIP_TALK_MESSAGE;
		tTripTalkSubPacket.ChatRoomTitle = ChatTitle;
		tTripTalkSubPacket.ChatMessage = Message;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void TripTalkParticipantsApply_Sender(boolean DistributionFlag) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		tTripTalkSubPacket.SubType = DEFINE.TRIP_TALK_DISTRIBUTION;
		tTripTalkSubPacket.TlaverDistributionApply = true;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	// 0924
	public void TripTalkBoardClick_Sender(String ChatTitle) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		tTripTalkSubPacket.SubType = DEFINE.TRIP_TALK_BOARD_CLICK;
		tTripTalkSubPacket.ChatRoomTitle = ChatTitle;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void TripTalkBoardPublish_Sender(String UserID, String RoomTitle, File BoardData, String BoardInfo) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		mMainPacket.UserID = UserID;
		tTripTalkSubPacket.SubType = DEFINE.TRIP_TALK_BOARD_PUBLISH;
		tTripTalkSubPacket.ChatRoomTitle = RoomTitle;
		tTripTalkSubPacket.BoardData = BoardData;
		tTripTalkSubPacket.BoardInfo = BoardInfo;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void TripTalkBoardDelete_Sender(String UserID, String RoomTitle, int BoardNumber) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		mMainPacket.UserID = UserID;
		tTripTalkSubPacket.SubType = DEFINE.TRIP_TALK_BOARD_DELETE;
		tTripTalkSubPacket.ChatRoomTitle = RoomTitle;
		tTripTalkSubPacket.BoardNumber = BoardNumber;
		tTripTalkSubPacket.DeleteFlag = true;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void TripTalkBoardEdit_Sender(String UserID, String RoomTitle, File BoardData, String BoardInfo, int BoardNumber) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		mMainPacket.UserID = UserID;
		tTripTalkSubPacket.SubType = DEFINE.TRIP_TALK_BOARD_EDIT;
		tTripTalkSubPacket.ChatRoomTitle = RoomTitle;
		tTripTalkSubPacket.BoardData = BoardData;
		tTripTalkSubPacket.BoardInfo = BoardInfo;
		tTripTalkSubPacket.BoardNumber = BoardNumber;
		tTripTalkSubPacket.EditFlag = true;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void TripTalkBoardReply_Sender(String UserID, String RoomTitle, String Reply, int BoardNumber) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		mMainPacket.UserID = UserID;
		tTripTalkSubPacket.SubType = DEFINE.TRIP_TALK_BOARD_REPLY;
		tTripTalkSubPacket.ChatRoomTitle = RoomTitle;
		tTripTalkSubPacket.Reply = Reply;
		tTripTalkSubPacket.BoardNumber = BoardNumber;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void TripTalkBoardReplyEdit_Sender(String UserID, String RoomTitle, String Reply, int BoardNumber) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		mMainPacket.UserID = UserID;
		tTripTalkSubPacket.SubType = DEFINE.TRIP_TALK_BOARD_REPLY_EDIT;
		tTripTalkSubPacket.ChatRoomTitle = RoomTitle;
		tTripTalkSubPacket.Reply = Reply;
		tTripTalkSubPacket.BoardNumber = BoardNumber;
		tTripTalkSubPacket.EditFlag = true;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void TripTalkBoardReplyDelete_Sender(String UserID, String RoomTitle, String Reply, int BoardNumber) throws IOException {
		MainPacketClear();
		TripTalkPacketClear();
		mMainPacket.DataType = DEFINE.TRIPTALK_PACKET;
		mMainPacket.UserID = UserID;
		tTripTalkSubPacket.SubType = DEFINE.TRIP_TALK_BOARD_REPLY_DELETE;
		tTripTalkSubPacket.ChatRoomTitle = RoomTitle;
		tTripTalkSubPacket.Reply = Reply;
		tTripTalkSubPacket.BoardNumber = BoardNumber;
		tTripTalkSubPacket.DeleteFlag = true;

		Serialize_TripTalk();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}


	//////���� Ȥ�� �ٸ���� ������ ���� 

	public void PageTabClick_Sender(String UserID) throws IOException {
		MainPacketClear();
		PagePacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.PAGE_PACKET;
		pPageSubPacket.SubType = DEFINE.PAGE_TAB_CLICK;

		Serialize_Page();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PageStep_Sender(String UserID, int Step) throws IOException {
		MainPacketClear();
		PagePacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.PAGE_PACKET;
		pPageSubPacket.SubType = DEFINE.PAGE_STEP;
		pPageSubPacket.Step = Step;

		Serialize_Page();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PageInsertOtherPage_Sender(String UserID, String ClickedID) throws IOException {
		MainPacketClear();
		PagePacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.PAGE_PACKET;
		pPageSubPacket.SubType = DEFINE.PAGE_INSERT_OTHER_PAGE;
		pPageSubPacket.ClickedID = ClickedID;
		pPageSubPacket.WhoClicked = true;

		Serialize_Page();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PagePersonalMessge_Sender(String UserID, String ClickedID, String Message) throws IOException {
		MainPacketClear();
		PagePacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.PAGE_PACKET;
		pPageSubPacket.SubType = DEFINE.PAGE_PERSONAL_MESSAGE;
		pPageSubPacket.ClickedID = ClickedID;
		pPageSubPacket.PersonalMessage = Message;

		Serialize_Page();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PageProfileEdit_Sender(String UserID, String ProfileInfo, File ProfileData) throws IOException {
		MainPacketClear();
		PagePacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.PAGE_PACKET;
		pPageSubPacket.SubType = DEFINE.PAGE_EDIT_PROFILE;
		pPageSubPacket.ProfileInfo = ProfileInfo;
		pPageSubPacket.ProfileData = ProfileData;

		Serialize_Page();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	//------------------------------�ȷο�-------------------------------------------------//

	public void FollowApply_Sender(String UserID, String FollowID) throws IOException {
		MainPacketClear();
		FollowPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.FOLLOW_PACKET;
		fFollowSubPacket.SubType = DEFINE.FOLLOW_APPLY;
		fFollowSubPacket.FollowID = FollowID;
		fFollowSubPacket.FollowCheck = true;

		Serialize_Follow();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void UNFollowApply_Sender(String UserID, String FollowID) throws IOException {
		MainPacketClear();
		FollowPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.FOLLOW_PACKET;
		fFollowSubPacket.SubType = DEFINE.UNFOLLOW_APLLY;
		fFollowSubPacket.FollowID = FollowID;
		fFollowSubPacket.FollowCheck = false;

		Serialize_Follow();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}


	//-----------------------------�Խù�-----------------------------------------------//


	public void PostTabClick_Sender(String UserID, boolean TripStatus) throws IOException {
		MainPacketClear();
		PostPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.POST_PACKET;
		pPostSubPacket.SubType = DEFINE.POST_TAB_CLICK;
		pPostSubPacket.TripStatus = TripStatus;

		Serialize_post();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PostPublish_Sender(String UserID, String Time, String PostInfo, File PostData, boolean PublishFlag) throws IOException {
		MainPacketClear();
		PostPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.POST_PACKET;
		mMainPacket.Time = Time;
		pPostSubPacket.SubType = DEFINE.POST_PUBLISH;
		pPostSubPacket.PostInfo = PostInfo;
		pPostSubPacket.PostData = PostData;
		pPostSubPacket.PublishFlag = PublishFlag;

		Serialize_post();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PostEdit_Sender(String UserID, String Time, String PostInfo, File PostData, int PostNumber) throws IOException {
		MainPacketClear();
		PostPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.POST_PACKET;
		mMainPacket.Time = Time;
		pPostSubPacket.SubType = DEFINE.POST_EDIT;
		pPostSubPacket.PostInfo = PostInfo;
		pPostSubPacket.PostData = PostData;
		pPostSubPacket.PostNumber = PostNumber;
		pPostSubPacket.EditFlag = true;

		Serialize_post();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PostDelete_Sender(String UserID, int PostNumber) throws IOException {
		MainPacketClear();
		PostPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.POST_PACKET;
		pPostSubPacket.SubType = DEFINE.POST_DELETE;
		pPostSubPacket.PostNumber = PostNumber;
		pPostSubPacket.DeleteFlag = true;

		Serialize_post();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PostReplyPublish_Sender(String UserID, String Reply, int PostNumber) throws IOException {
		MainPacketClear();
		PostPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.POST_PACKET;
		pPostSubPacket.SubType = DEFINE.POST_REPLY_PUBLISH;
		pPostSubPacket.Reply = Reply;
		pPostSubPacket.PostNumber = PostNumber;

		Serialize_post();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PostReplyEdit_Sender(String UserID, String Reply, int PostNumber) throws IOException {
		MainPacketClear();
		PostPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.POST_PACKET;
		pPostSubPacket.SubType = DEFINE.POST_REPLY_EDIT;
		pPostSubPacket.Reply = Reply;
		pPostSubPacket.PostNumber = PostNumber;

		Serialize_post();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PostReplyDelete_Sender(String UserID, String Reply, int PostNumber) throws IOException {
		MainPacketClear();
		PostPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.POST_PACKET;
		pPostSubPacket.SubType = DEFINE.POST_REPLY_DELETE;
		pPostSubPacket.Reply = Reply;
		pPostSubPacket.PostNumber = PostNumber;

		Serialize_post();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PostReset_Sender(String UserID, int PostIndex) throws IOException {
		MainPacketClear();
		PostPacketClear();
		mMainPacket.UserID = UserID;
		mMainPacket.DataType = DEFINE.POST_PACKET;
		pPostSubPacket.SubType = DEFINE.POST_TAB_RESET;
		pPostSubPacket.PostNumber = PostIndex;

		Serialize_post();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}

	public void PostLike_Sender(int PostIndex) throws IOException {
		MainPacketClear();
		PostPacketClear();
		mMainPacket.DataType = DEFINE.POST_PACKET;
		pPostSubPacket.SubType = DEFINE.POST_LIKE;
		pPostSubPacket.PostNumber = PostIndex;

		Serialize_post();

		Runnable sender = new Sender(TotalyPacket);
		Thread Sender = new Thread(sender);
		Sender.start();
	}
}