package com.nslb.twipee.communication;

import org.apache.commons.lang3.SerializationUtils;

public class DataPacketAnalysis {

	private DefineValue DEFINE = new DefineValue();
	private GUIInterface_Receiver Interface = new GUIInterface_Receiver();
	private int MainSize;
	private int SubSize;
	private byte[] HeaderByteArray = new byte[DEFINE.HEADER_SIZE];
	private byte[] MainByteArray;
	private byte[] SubByteArray;
	private byte[] HeaderReset = new byte[DEFINE.HEADER_SIZE];
	private byte[] MainReset;
	private byte[] SubReset;

	private DataPacketStore.header hHeaderPacket = new DataPacketStore.header();
	private DataPacketStore.MainPacket mMainPacket = new DataPacketStore.MainPacket();
	private DataPacketStore.LoginSubPacket lLoginPacket = new DataPacketStore.LoginSubPacket();
	private DataPacketStore.TripTalkSubPacket tTripTalkSubPacket = new DataPacketStore.TripTalkSubPacket();
	private DataPacketStore.FollowSubPacket fFollowSubPacket = new DataPacketStore.FollowSubPacket();
	private DataPacketStore.PageSubPacket pPageSubPacket = new DataPacketStore.PageSubPacket();
	private DataPacketStore.PostSubPacket pPostSubPacket = new DataPacketStore.PostSubPacket();

	//Byte[] -> int[]
	public int[] convert(byte buf[]) {
		int intArr[] = new int[buf.length / 4];
		int offset = 0;
		for (int i = 0; i < intArr.length; i++) {
			intArr[i] = (buf[3 + offset] & 0xFF) | ((buf[2 + offset] & 0xFF) << 8) |
					((buf[1 + offset] & 0xFF) << 16) | ((buf[0 + offset] & 0xFF) << 24);
			offset += 4;
		}
		return intArr;
	}

	private void InitSize(int mainsize, int subsize) {
		this.MainSize = mainsize;
		this.SubSize = subsize;
		this.MainByteArray = new byte[MainSize];
		this.SubByteArray = new byte[SubSize];
		this.MainReset = new byte[MainSize];
		this.SubReset = new byte[SubSize];
	}

	private void Header_MainDesrialize(byte[] DataPacket) {
		System.arraycopy(DataPacket, 0, HeaderByteArray, 0, HeaderByteArray.length);
		hHeaderPacket.header = convert(HeaderByteArray);
		MainSize = hHeaderPacket.header[0];
		SubSize = hHeaderPacket.header[1];
		InitSize(MainSize, SubSize);

		System.arraycopy(DataPacket, HeaderByteArray.length, MainByteArray, 0, MainSize);
		mMainPacket = (DataPacketStore.MainPacket) SerializationUtils.deserialize(MainByteArray);
		SubPacketDeserialize(DataPacket);
	}

	private void SubPacketDeserialize(byte[] DataPacket) {
		int headersize = hHeaderPacket.header[1];
		byte[] Subpacket = new byte[headersize];

		System.arraycopy(DataPacket, MainSize + DEFINE.HEADER_SIZE, Subpacket, 0, headersize);

		if (mMainPacket.DataType == DEFINE.LOGIN_PACKET) {
			lLoginPacket = SerializationUtils.deserialize(Subpacket);
		} else if (mMainPacket.DataType == DEFINE.TRIPTALK_PACKET) {
			tTripTalkSubPacket = SerializationUtils.deserialize(Subpacket);
		} else if (mMainPacket.DataType == DEFINE.FOLLOW_PACKET) {
			fFollowSubPacket = SerializationUtils.deserialize(Subpacket);
		} else if (mMainPacket.DataType == DEFINE.PAGE_PACKET) {
			pPageSubPacket = SerializationUtils.deserialize(Subpacket);
		}

		System.arraycopy(HeaderReset, 0, HeaderByteArray, 0, HeaderReset.length);
		System.arraycopy(MainReset, 0, MainByteArray, 0, MainByteArray.length);
		System.arraycopy(SubReset, 0, SubByteArray, 0, SubByteArray.length);
	}

	public void PacketAnalysis(byte[] DataPacket)
	{
		Header_MainDesrialize(DataPacket);

		if (mMainPacket.DataType == DEFINE.LOGIN_PACKET) {
			if (lLoginPacket.SubType == DEFINE.LOGIN_SUCCESS) {
				Interface.LoginGUI_Login(lLoginPacket.LoginSuccessFlag);
			} else if (lLoginPacket.SubType == DEFINE.LOGOUT_SUCCESS) {
				Interface.LoginGUI_Logout(lLoginPacket.LogoutSuccessFlag);
			} else if (lLoginPacket.SubType == DEFINE.JOIN_SUCCESS) {
				Interface.LoginGUI_Join(lLoginPacket.JoinSuccessFlag);
			} else if (lLoginPacket.SubType == DEFINE.ID_DUPLICATE_CHECK) {
				Interface.LoginGUI_Duplicate(lLoginPacket.IDDuplicateFlag);
			} else if (lLoginPacket.SubType == DEFINE.ID_SEARCHED) {
				Interface.LoginGUI_SearchedID(lLoginPacket.SearchedID, lLoginPacket.SearchedFlag);
			} else if (lLoginPacket.SubType == DEFINE.PW_SEARCHED) {
				Interface.LoginGUI_SearchedPW(lLoginPacket.SearchedPW, lLoginPacket.SearchedFlag);
			} else if (lLoginPacket.SubType == DEFINE.USER_IMFORMATION_EDIT) {
				Interface.LoginGUI_ImformationEdit(lLoginPacket.EditFlag);
			}
		} else if (mMainPacket.DataType == DEFINE.TRIPTALK_PACKET) {
			if (tTripTalkSubPacket.SubType == DEFINE.PARTICIPATE_NOTICE) {
				Interface.TripTalkGUI_ParticipateNotice(tTripTalkSubPacket.InsertLeaveID, tTripTalkSubPacket.ChatRoomTitle, tTripTalkSubPacket.InsertFlag);
			} else if (tTripTalkSubPacket.SubType == DEFINE.LEAVE_PARTICIPATE_NOTICE) {
				Interface.TripTalkGUI_ParticipateLeaveNotice(tTripTalkSubPacket.InsertLeaveID, tTripTalkSubPacket.ChatRoomTitle, tTripTalkSubPacket.InsertFlag);
			} else if (tTripTalkSubPacket.SubType == DEFINE.PARTICIPANTS_NOTICE) {
				Interface.TripTalkGUI_Participants(tTripTalkSubPacket.Participants, tTripTalkSubPacket.ChatRoomTitle);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_MESSAGE) {
				Interface.TripTalkGUI_RecvTripTalkMessage(tTripTalkSubPacket.ChatID, tTripTalkSubPacket.ChatRoomTitle, tTripTalkSubPacket.ChatMessage);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_DISTRIBUTION) {
				Interface.TripTalkGUI_TravlerDistributionNotice(tTripTalkSubPacket.ChatRoomTitle, tTripTalkSubPacket.TlaverDistribution);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_BOARD_CLICK) {
				Interface.TripTalkGUI_BoardTab(mMainPacket.Time, tTripTalkSubPacket.ChatRoomTitle, tTripTalkSubPacket.ChatID, tTripTalkSubPacket.Reply, tTripTalkSubPacket.BoardInfo, tTripTalkSubPacket.BoardData, tTripTalkSubPacket.BoardNumber);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_BOARD_PUBLISH) {
				Interface.TripTalkGUI_BoardPublishSuccess(tTripTalkSubPacket.PublishSuccessFlag);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_BOARD_DELETE) {
				Interface.TripTalkGUI_BoardDeleteSuccess(tTripTalkSubPacket.DeleteFlag);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_BOARD_EDIT) {
				Interface.TripTalkGUI_BoardEditSuccess(tTripTalkSubPacket.EditFlag);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_BOARD_REPLY) {
				Interface.TripTalkGUI_BoardReplySuccess(tTripTalkSubPacket.PublishSuccessFlag);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_BOARD_REPLY_EDIT) {
				Interface.TripTalkGUI_BoardReplyEdit(tTripTalkSubPacket.EditFlag);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_BOARD_REPLY_DELETE) {
				Interface.TripTalkGUI_BoardReplyDelete(tTripTalkSubPacket.DeleteFlag);
			} else if (tTripTalkSubPacket.SubType == DEFINE.TRIP_TALK_ROOM_INSERT) {
				Interface.TripTalkGUI_ParticipateInsertNotice(tTripTalkSubPacket.InsertLeaveID, tTripTalkSubPacket.ChatRoomTitle, tTripTalkSubPacket.InsertFlag);
			}
		}

		else if (mMainPacket.DataType == DEFINE.PAGE_PACKET) {
			if (pPageSubPacket.SubType == DEFINE.PAGE_TAB_CLICK) {
				Interface.PageGUI_PersionalPage(pPageSubPacket.ProfileInfo, pPageSubPacket.ProfileData, pPageSubPacket.PostData);
			} else if (pPageSubPacket.SubType == DEFINE.PAGE_INSERT_OTHER_PAGE) {
				Interface.PageGUI_OtherPage(pPageSubPacket.ProfileInfo, pPageSubPacket.ProfileData, pPageSubPacket.PostData);
			} else if (pPageSubPacket.SubType == DEFINE.PAGE_PERSONAL_MESSAGE) {
				Interface.PageGUI_PersonalMessage(pPageSubPacket.ChatID, pPageSubPacket.PersonalMessage, mMainPacket.Time);
			} else if (pPageSubPacket.SubType == DEFINE.PAGE_EDIT_PROFILE) {
				Interface.PageGUI_ProfileEditComplete(pPageSubPacket.EditFlag);
			}
		}

		else if (mMainPacket.DataType == DEFINE.FOLLOW_PACKET) {
			if (fFollowSubPacket.SubType == DEFINE.FOLLOW_APPLY) {
				if (fFollowSubPacket.FollowCheck == true) {
					Interface.FollowGUI_FollowAram(fFollowSubPacket.FollowID);
				}
			} else if (fFollowSubPacket.SubType == DEFINE.UNFOLLOW_APLLY) {
				if (fFollowSubPacket.FollowCheck == false) {
					Interface.FollowGUI_UNFollowAram(fFollowSubPacket.FollowID);
				}
			}
		}

		else if (mMainPacket.DataType == DEFINE.POST_PUBLISH) {
			if (pPostSubPacket.SubType == DEFINE.POST_TAB_CLICK) {
				Interface.PostGUI_PostTabClick(pPostSubPacket.PublisherID, pPostSubPacket.PostNumber, pPostSubPacket.Reply, pPostSubPacket.PostInfo, pPostSubPacket.PostData);
			} else if (pPostSubPacket.SubType == DEFINE.POST_PUBLISH) {
				Interface.PostGUI_PostPublish(pPostSubPacket.PublishFlag);
			} else if (pPostSubPacket.SubType == DEFINE.POST_EDIT) {
				Interface.PostGUI_PostEdit(pPostSubPacket.EditFlag);
			} else if (pPostSubPacket.SubType == DEFINE.POST_DELETE) {
				Interface.PostGUI_PostEdit(pPostSubPacket.DeleteFlag);
			} else if (pPostSubPacket.SubType == DEFINE.POST_REPLY_PUBLISH) {
				Interface.PostGUI_PostEdit(pPostSubPacket.PublishFlag);
			} else if (pPostSubPacket.SubType == DEFINE.POST_REPLY_EDIT) {
				Interface.PostGUI_PostEdit(pPostSubPacket.EditFlag);
			} else if (pPostSubPacket.SubType == DEFINE.POST_REPLY_DELETE) {
				Interface.PostGUI_PostEdit(pPostSubPacket.DeleteFlag);
			} else if (pPostSubPacket.SubType == DEFINE.POST_TAB_RESET) {
				Interface.PostGUI_PostTabReset(pPostSubPacket.PublisherID, pPostSubPacket.PostNumber, pPostSubPacket.Reply, pPostSubPacket.PostInfo, pPostSubPacket.PostData);
			}
		}
	}
}