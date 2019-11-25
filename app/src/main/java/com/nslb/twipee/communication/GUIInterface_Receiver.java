package com.nslb.twipee.communication;

import java.io.File;

public class GUIInterface_Receiver {

    public void LoginGUI_Login(boolean LoginFLAG) {

    }

    public void LoginGUI_Logout(boolean LogoutFLAG) {

    }

    public void LoginGUI_Join(boolean JoinFLAG) {

    }

    public void LoginGUI_Duplicate(boolean IDDuplicateFLAG) {

    }

    public void LoginGUI_SearchedID(String SearchedID, boolean SearchedFLAG) {
    int a = 0;
    }

    public void LoginGUI_SearchedPW(String SearchedPW, boolean SearchedFLAG) {

    }

    public void LoginGUI_ImformationEdit(boolean EditFLAG) {

    }

    //----------------------Ʈ����-------------------------------------------------------------------------------------------------------//
    public void TripTalkGUI_ParticipateNotice(String ParticipantID, String ChatTitle, boolean Flag) {

    }

    public void TripTalkGUI_ParticipateInsertNotice(String ParticipantID, String ChatTitle, boolean Flag) {

    }

    public void TripTalkGUI_ParticipateLeaveNotice(String ParticipantID, String ChatTitle, boolean Flag) {

    }

    public void TripTalkGUI_Participants(String Participants, String ChatTitle) {

    }


    public void TripTalkGUI_RecvTripTalkMessage(String ID, String ChatTitle, String Message) {

    }

    public void TripTalkGUI_TravlerDistributionNotice(String ChatTitel, char[] TravlerDistribution) {

    }
    //0924

    public void TripTalkGUI_BoardTab(String Time, String ChatTitle, String PublisherID, String Reply, String BoardInfo, File BoardImage, int BoardNumber) {

    }

    public void TripTalkGUI_BoardPublishSuccess(boolean SuccessFlag) {

    }

    public void TripTalkGUI_BoardDeleteSuccess(boolean DeleteFlag) {

    }

    public void TripTalkGUI_BoardEditSuccess(boolean EditFlag) {

    }

    public void TripTalkGUI_BoardReplySuccess(boolean PublishFlag) {

    }

    public void TripTalkGUI_BoardReplyEdit(boolean EditFlag) {

    }

    public void TripTalkGUI_BoardReplyDelete(boolean DeleteFlag) {

    }

    //------------------------������---------------------------------------------------------------------//

    public void PageGUI_PersionalPage(String ProfileInfo, File ProfileData, File PostData) {

    }

    public void PageGUI_OtherPage(String ProfileInfo, File ProfileData, File PostData) {

    }


    public void PageGUI_PersonalMessage(String UserID, String Message, String Time) {

    }

    public void PageGUI_ProfileEditComplete(boolean Flag) {

    }

    //�Խù�-------------------------------------------------------------------------------------------------//

    public void PostGUI_PostTabClick(String PublisherID, int PostNumber, String Reply, String PostInfo, File PostData) {

    }

    public void PostGUI_PostPublish(boolean PublishFlag) {

    }

    public void PostGUI_PostEdit(boolean EditFlag) {

    }

    public void PostGUI_PostDelete(boolean DeleteFlag) {

    }

    public void PostGUI_ReplyPublish(boolean PublishFlag) {

    }

    public void PostGUI_ReplyEdit(boolean EditFlag) {

    }

    public void PostGUI_ReplyDelete(boolean DeleteFlag) {

    }

    public void PostGUI_PostTabReset(String PublisherID, int PostNumber, String Reply, String PostInfo, File PostData) {

    }


    //------------------------------------�ȷο�----------------------------------------------------------------//

    public void FollowGUI_FollowAram(String FollowerID) {

    }

    public void FollowGUI_UNFollowAram(String FollowerID) {

    }
}