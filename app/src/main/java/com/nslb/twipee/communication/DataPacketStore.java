package com.nslb.twipee.communication;//package src;
import java.io.File;
import java.io.Serializable;

public class DataPacketStore {

	static public class header
	{
		public int[] header = new int[2];
	}

	static public class MainPacket implements Serializable
	{
		private static final long serialVersionUID = 1L;
		public int PacketSize;
		public int DataType;
		public String UserID;
		public String Time;
	}

	static public class LoginSubPacket implements Serializable
	{
		private static final long serialVersionUID = 1L;
		public int SubType;
		public String UserPW;
		public String UserName;
		public String UserEmail;
		public String PhoneNumber;
		public String BirthDay;
		public String SearchedID;
		public String SearchedPW;
		public boolean SearchedFlag;
		public boolean LoginSuccessFlag;
		public boolean LogoutSuccessFlag;
		public boolean IDDuplicateFlag;
		public boolean JoinSuccessFlag;
		public boolean EditFlag;
	}
	
	static public class TripTalkSubPacket implements Serializable
	{
		public int SubType;
		public int BoardNumber;
		public String Participants;
		public String ChatRoomTitle;
		public String ChatID;
		public String InsertLeaveID;
		public String ChatMessage;
		public String Reply;
		public String BoardInfo;
		public File ChatImageBuffer;
		public File BoardData;
		public boolean InsertFlag;
		public boolean BoardPublishFlag;
		public boolean DeleteFlag;
		public boolean EditFlag;
		public boolean PublishSuccessFlag;
		public boolean TlaverDistributionApply;
		public char[] TlaverDistribution;
	}
	
	static public class FollowSubPacket implements Serializable
	{
		public int SubType;
		public String FollowID;
		public boolean FollowCheck;
	}
	
	static public class PostSubPacket
	{
		public int SubType;
		public int PostNumber;
		public String PublisherID;
		public String Reply;
		public String PostInfo;
		public File PostData;
		public boolean TripStatus;
		public boolean PublishFlag;
		public boolean EditFlag;
		public boolean DeleteFlag;
	}
	
	static public class PageSubPacket
	{
		public int SubType;
		public int Step;
		public String ChatID;
		public String ClickedID;
		public String PersonalMessage;
		public String ProfileInfo;
		public File ProfileData;
		public File PostData;
		public boolean WhoClicked;
		public boolean EditFlag;
	}	
}