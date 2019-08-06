package me.nextgeneric.telegram;

import com.github.badoualy.telegram.api.TelegramApiStorage;
import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.api.utils.InputFileLocation;
import com.github.badoualy.telegram.mtproto.auth.AuthKey;
import com.github.badoualy.telegram.mtproto.model.DataCenter;
import com.github.badoualy.telegram.tl.api.*;
import com.github.badoualy.telegram.tl.api.account.*;
import com.github.badoualy.telegram.tl.api.auth.TLAuthorization;
import com.github.badoualy.telegram.tl.api.auth.*;
import com.github.badoualy.telegram.tl.api.channels.TLChannelParticipant;
import com.github.badoualy.telegram.tl.api.channels.TLChannelParticipants;
import com.github.badoualy.telegram.tl.api.contacts.*;
import com.github.badoualy.telegram.tl.api.help.TLAbsAppUpdate;
import com.github.badoualy.telegram.tl.api.help.TLInviteText;
import com.github.badoualy.telegram.tl.api.help.TLSupport;
import com.github.badoualy.telegram.tl.api.help.TLTermsOfService;
import com.github.badoualy.telegram.tl.api.messages.*;
import com.github.badoualy.telegram.tl.api.messages.TLChatFull;
import com.github.badoualy.telegram.tl.api.messages.TLStickerSet;
import com.github.badoualy.telegram.tl.api.payments.*;
import com.github.badoualy.telegram.tl.api.phone.TLPhoneCall;
import com.github.badoualy.telegram.tl.api.photos.TLAbsPhotos;
import com.github.badoualy.telegram.tl.api.photos.TLPhoto;
import com.github.badoualy.telegram.tl.api.updates.TLAbsChannelDifference;
import com.github.badoualy.telegram.tl.api.updates.TLAbsDifference;
import com.github.badoualy.telegram.tl.api.updates.TLState;
import com.github.badoualy.telegram.tl.api.upload.TLAbsCdnFile;
import com.github.badoualy.telegram.tl.api.upload.TLAbsFile;
import com.github.badoualy.telegram.tl.api.upload.TLFile;
import com.github.badoualy.telegram.tl.api.upload.TLWebFile;
import com.github.badoualy.telegram.tl.core.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rx.Observable;

import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ArtificialTelegramClient implements TelegramClient {


    public ArtificialTelegramClient(TelegramApiStorage telegramApiStorage) {
        byte[] randomBytes = new byte[256];
        ThreadLocalRandom.current().nextBytes(randomBytes);
        telegramApiStorage.saveAuthKey(new AuthKey(randomBytes));
        telegramApiStorage.saveDc(new DataCenter("dc.nextnode.com", 28880));
    }

    @NotNull
    @Override
    public TLAuthorization authCheckPassword(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUser accountChangePhone(String s, String s1, String s2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountCheckUsername(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountConfirmPhone(String s, String s1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountDeleteAccount(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAccountDaysTTL accountGetAccountTTL() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAuthorizations accountGetAuthorizations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsPeerNotifySettings accountGetNotifySettings(TLAbsInputNotifyPeer tlAbsInputNotifyPeer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsPassword accountGetPassword() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPasswordSettings accountGetPasswordSettings(TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPrivacyRules accountGetPrivacy(TLAbsInputPrivacyKey tlAbsInputPrivacyKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLTmpPassword accountGetTmpPassword(TLBytes tlBytes, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLVector<TLAbsWallPaper> accountGetWallPapers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountRegisterDevice(int i, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountReportPeer(TLAbsInputPeer tlAbsInputPeer, TLAbsReportReason tlAbsReportReason) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountResetAuthorization(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountResetNotifySettings() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLSentCode accountSendChangePhoneCode(boolean b, String s, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLSentCode accountSendConfirmPhoneCode(boolean b, String s, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountSetAccountTTL(TLAccountDaysTTL tlAccountDaysTTL) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPrivacyRules accountSetPrivacy(TLAbsInputPrivacyKey tlAbsInputPrivacyKey, TLVector<TLAbsInputPrivacyRule> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountUnregisterDevice(int i, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountUpdateDeviceLocked(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountUpdateNotifySettings(TLAbsInputNotifyPeer tlAbsInputNotifyPeer, TLInputPeerNotifySettings tlInputPeerNotifySettings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountUpdatePasswordSettings(TLBytes tlBytes, TLPasswordInputSettings tlPasswordInputSettings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUser accountUpdateProfile(String s, String s1, String s2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool accountUpdateStatus(boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUser accountUpdateUsername(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool authBindTempAuthKey(long l, long l1, int i, TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool authCancelCode(String s, String s1) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public TLAuthorization authCheckPassword(TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLCheckedPhone authCheckPhone(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool authDropTempAuthKeys(TLLongVector tlLongVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLExportedAuthorization authExportAuthorization(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAuthorization authImportAuthorization(int i, TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAuthorization authImportBotAuthorization(int i, int i1, String s, String s1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool authLogOut() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAuthorization authRecoverPassword(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPasswordRecovery authRequestPasswordRecovery() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLSentCode authResendCode(String s, String s1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool authResetAuthorizations() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public TLSentCode authSendCode(boolean b, String s, boolean b1) {
        log.info("Send code to {}", s);
        TLSentCode tlSentCode = new TLSentCode();
        tlSentCode.setPhoneCodeHash("hash");
        return tlSentCode;
    }

    @NotNull
    @Override
    public TLSentCode authSendCode(boolean b, String s, boolean b1, int i, String s1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool authSendInvites(TLStringVector tlStringVector, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAuthorization authSignIn(String s, String s1, String s2) {
        TLUser tlAbsUser = new TLUser();
        tlAbsUser.setId((int) (System.currentTimeMillis() % Integer.MAX_VALUE));
        tlAbsUser.setFirstName("Vladimir");
        tlAbsUser.setLastName("Astakhov");
        tlAbsUser.setPhone("Phone");
        log.info("Successful sign-in");
        return new TLAuthorization(1, tlAbsUser);
    }

    @Override
    public TLAuthorization authSignUp(String s, String s1, String s2, String s3, String s4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool botsAnswerWebhookJSONQuery(long l, TLDataJSON tlDataJSON) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLDataJSON botsSendCustomRequest(String s, TLDataJSON tlDataJSON) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool channelsCheckUsername(TLAbsInputChannel tlAbsInputChannel, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsCreateChannel(boolean b, boolean b1, String s, String s1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsDeleteChannel(TLAbsInputChannel tlAbsInputChannel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAffectedMessages channelsDeleteMessages(TLAbsInputChannel tlAbsInputChannel, TLIntVector tlIntVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAffectedHistory channelsDeleteUserHistory(TLAbsInputChannel tlAbsInputChannel, TLAbsInputUser tlAbsInputUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool channelsEditAbout(TLAbsInputChannel tlAbsInputChannel, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsEditAdmin(TLAbsInputChannel tlAbsInputChannel, TLAbsInputUser tlAbsInputUser, TLAbsChannelParticipantRole tlAbsChannelParticipantRole) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsEditPhoto(TLAbsInputChannel tlAbsInputChannel, TLAbsInputChatPhoto tlAbsInputChatPhoto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsEditTitle(TLAbsInputChannel tlAbsInputChannel, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsExportedChatInvite channelsExportInvite(TLAbsInputChannel tlAbsInputChannel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLExportedMessageLink channelsExportMessageLink(TLAbsInputChannel tlAbsInputChannel, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsChats channelsGetAdminedPublicChannels() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsChats channelsGetChannels(TLVector<TLAbsInputChannel> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLChatFull channelsGetFullChannel(TLAbsInputChannel tlAbsInputChannel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsMessages channelsGetMessages(TLAbsInputChannel tlAbsInputChannel, TLIntVector tlIntVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLChannelParticipant channelsGetParticipant(TLAbsInputChannel tlAbsInputChannel, TLAbsInputUser tlAbsInputUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLChannelParticipants channelsGetParticipants(TLAbsInputChannel tlAbsInputChannel, TLAbsChannelParticipantsFilter tlAbsChannelParticipantsFilter, int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsInviteToChannel(TLAbsInputChannel tlAbsInputChannel, TLVector<TLAbsInputUser> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsJoinChannel(TLAbsInputChannel tlAbsInputChannel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsKickFromChannel(TLAbsInputChannel tlAbsInputChannel, TLAbsInputUser tlAbsInputUser, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsLeaveChannel(TLAbsInputChannel tlAbsInputChannel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool channelsReadHistory(TLAbsInputChannel tlAbsInputChannel, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool channelsReportSpam(TLAbsInputChannel tlAbsInputChannel, TLAbsInputUser tlAbsInputUser, TLIntVector tlIntVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsToggleInvites(TLAbsInputChannel tlAbsInputChannel, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsToggleSignatures(TLAbsInputChannel tlAbsInputChannel, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates channelsUpdatePinnedMessage(boolean b, TLAbsInputChannel tlAbsInputChannel, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool channelsUpdateUsername(TLAbsInputChannel tlAbsInputChannel, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool contactsBlock(TLAbsInputUser tlAbsInputUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLLink contactsDeleteContact(TLAbsInputUser tlAbsInputUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool contactsDeleteContacts(TLVector<TLAbsInputUser> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLIntVector contactsExportCard() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsBlocked contactsGetBlocked(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsContacts contactsGetContacts(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLVector<TLContactStatus> contactsGetStatuses() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsTopPeers contactsGetTopPeers(boolean b, boolean b1, boolean b2, boolean b3, boolean b4, int i, int i1, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUser contactsImportCard(TLIntVector tlIntVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLImportedContacts contactsImportContacts(TLVector<TLInputPhoneContact> tlVector, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool contactsResetTopPeerRating(TLAbsTopPeerCategory tlAbsTopPeerCategory, TLAbsInputPeer tlAbsInputPeer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLResolvedPeer contactsResolveUsername(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLFound contactsSearch(String s, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool contactsUnblock(TLAbsInputUser tlAbsInputUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool contestSaveDeveloperInfo(int i, String s, String s1, int i1, String s2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates helpGetAppChangelog(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsAppUpdate helpGetAppUpdate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLCdnConfig helpGetCdnConfig() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLConfig helpGetConfig() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLInviteText helpGetInviteText() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLNearestDc helpGetNearestDc() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLSupport helpGetSupport() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLTermsOfService helpGetTermsOfService() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool helpSaveAppLog(TLVector<TLInputAppEvent> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool helpSetBotUpdatesStatus(int i, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        log.info("Closing client");
    }

    @Override
    public void close(boolean b) {

    }

    @Override
    public void downloadSync(InputFileLocation inputFileLocation, int i, int i1, OutputStream outputStream) {
        log.info("Downloading nothing");
    }

    @Override
    public void downloadSync(InputFileLocation inputFileLocation, int i, OutputStream outputStream) {
        log.info("Downloading nothing");
    }

    @NotNull
    @Override
    public <T extends TLObject> List<T> executeRpcQueries(List<? extends TLMethod<T>> list) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public <T extends TLObject> List<T> executeRpcQueries(List<? extends TLMethod<T>> list, int i) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public <T extends TLObject> T executeRpcQuery(TLMethod<T> tlMethod) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public <T extends TLObject> T executeRpcQuery(TLMethod<T> tlMethod, int i) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public TLFile getChannelPhoto(TLAbsChat tlAbsChat, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public TLFile getChatPhoto(TLAbsChat tlAbsChat, boolean b) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public TelegramClient getDownloaderClient() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public TLFile getUserPhoto(TLAbsUser tlAbsUser, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends TLObject> T initConnection(int i, String s, String s1, String s2, String s3, TLMethod<T> tlMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends TLObject> T invokeAfterMsg(long l, TLMethod<T> tlMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends TLObject> T invokeAfterMsgs(TLLongVector tlLongVector, TLMethod<T> tlMethod) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public <T extends TLObject> T initConnection(TLMethod<T> tlMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends TLObject> T invokeWithLayer(int i, TLMethod<T> tlMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends TLObject> T invokeWithoutUpdates(TLMethod<T> tlMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsEncryptedChat messagesAcceptEncryption(TLInputEncryptedChat tlInputEncryptedChat, TLBytes tlBytes, long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesAddChatUser(int i, TLAbsInputUser tlAbsInputUser, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsChatInvite messagesCheckChatInvite(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesClearRecentStickers(boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesCreateChat(TLVector<TLAbsInputUser> tlVector, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesDeleteChatUser(int i, TLAbsInputUser tlAbsInputUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAffectedHistory messagesDeleteHistory(boolean b, TLAbsInputPeer tlAbsInputPeer, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAffectedMessages messagesDeleteMessages(boolean b, TLIntVector tlIntVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesDiscardEncryption(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesEditChatAdmin(int i, TLAbsInputUser tlAbsInputUser, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesEditChatPhoto(int i, TLAbsInputChatPhoto tlAbsInputChatPhoto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesEditChatTitle(int i, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesEditInlineBotMessage(boolean b, TLInputBotInlineMessageID tlInputBotInlineMessageID, String s, TLAbsReplyMarkup tlAbsReplyMarkup, TLVector<TLAbsMessageEntity> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesEditMessage(boolean b, TLAbsInputPeer tlAbsInputPeer, int i, String s, TLAbsReplyMarkup tlAbsReplyMarkup, TLVector<TLAbsMessageEntity> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsExportedChatInvite messagesExportChatInvite(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesForwardMessage(TLAbsInputPeer tlAbsInputPeer, int i, long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesForwardMessages(boolean b, boolean b1, boolean b2, TLAbsInputPeer tlAbsInputPeer, TLIntVector tlIntVector, TLLongVector tlLongVector, TLAbsInputPeer tlAbsInputPeer1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsChats messagesGetAllChats(TLIntVector tlIntVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesGetAllDrafts() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsAllStickers messagesGetAllStickers(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLArchivedStickers messagesGetArchivedStickers(boolean b, long l, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLVector<TLAbsStickerSetCovered> messagesGetAttachedStickers(TLAbsInputStickeredMedia tlAbsInputStickeredMedia) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBotCallbackAnswer messagesGetBotCallbackAnswer(boolean b, TLAbsInputPeer tlAbsInputPeer, int i, TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsChats messagesGetChats(TLIntVector tlIntVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsChats messagesGetCommonChats(TLAbsInputUser tlAbsInputUser, int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsDhConfig messagesGetDhConfig(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsDialogs messagesGetDialogs(boolean b, int i, int i1, TLAbsInputPeer tlAbsInputPeer, int i2) {

        TLAbsDialogs dialogs = new TLAbsDialogs() {
            @Override
            public int getConstructorId() {
                return 0;
            }
        };

        TLChannel channel = new TLChannel();
        channel.setUsername("lentach");
        channel.setId(100);
        channel.setAccessHash(223344L);
        dialogs.setChats(new TLVector<>());
        dialogs.getChats().add(channel);
        return dialogs;
    }

    @Override
    public TLAbsDocument messagesGetDocumentByHash(TLBytes tlBytes, int i, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsFeaturedStickers messagesGetFeaturedStickers(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLChatFull messagesGetFullChat(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLHighScores messagesGetGameHighScores(TLAbsInputPeer tlAbsInputPeer, int i, TLAbsInputUser tlAbsInputUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsMessages messagesGetHistory(TLAbsInputPeer tlAbsInputPeer, int i, int i1, int i2, int i3, int i4, int i5) {

        TLAbsMessages messages = new TLAbsMessages() {
            @Override
            public int getConstructorId() {
                return 0;
            }
        };

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messages.setMessages(new TLVector<>());

        if (ThreadLocalRandom.current().nextInt(10) < 8) {
            TLMessage tlAbsMessage = new TLMessage() {
                @Override
                public int getConstructorId() {
                    return 0;
                }
            };
            tlAbsMessage.setDate(23489283);
            tlAbsMessage.setMessage("Sample message");
            tlAbsMessage.setId(1);
            messages.getMessages().add(tlAbsMessage);
        }

        return messages;
    }

    @Override
    public TLBotResults messagesGetInlineBotResults(TLAbsInputUser tlAbsInputUser, TLAbsInputPeer tlAbsInputPeer, TLAbsInputGeoPoint tlAbsInputGeoPoint, String s, String s1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLHighScores messagesGetInlineGameHighScores(TLInputBotInlineMessageID tlInputBotInlineMessageID, TLAbsInputUser tlAbsInputUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsAllStickers messagesGetMaskStickers(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLMessageEditData messagesGetMessageEditData(TLAbsInputPeer tlAbsInputPeer, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsMessages messagesGetMessages(TLIntVector tlIntVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLIntVector messagesGetMessagesViews(TLAbsInputPeer tlAbsInputPeer, TLIntVector tlIntVector, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPeerDialogs messagesGetPeerDialogs(TLVector<TLAbsInputPeer> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPeerSettings messagesGetPeerSettings(TLAbsInputPeer tlAbsInputPeer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPeerDialogs messagesGetPinnedDialogs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsRecentStickers messagesGetRecentStickers(boolean b, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsSavedGifs messagesGetSavedGifs(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLStickerSet messagesGetStickerSet(TLAbsInputStickerSet tlAbsInputStickerSet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsWebPage messagesGetWebPage(String s, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsMessageMedia messagesGetWebPagePreview(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesHideReportSpam(TLAbsInputPeer tlAbsInputPeer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesImportChatInvite(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsStickerSetInstallResult messagesInstallStickerSet(TLAbsInputStickerSet tlAbsInputStickerSet, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesMigrateChat(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesReadEncryptedHistory(TLInputEncryptedChat tlInputEncryptedChat, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesReadFeaturedStickers(TLLongVector tlLongVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAffectedMessages messagesReadHistory(TLAbsInputPeer tlAbsInputPeer, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAffectedMessages messagesReadMessageContents(TLIntVector tlIntVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLVector<TLReceivedNotifyMessage> messagesReceivedMessages(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLLongVector messagesReceivedQueue(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesReorderPinnedDialogs(boolean b, TLVector<TLAbsInputPeer> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesReorderStickerSets(boolean b, TLLongVector tlLongVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesReportEncryptedSpam(TLInputEncryptedChat tlInputEncryptedChat) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesReportSpam(TLAbsInputPeer tlAbsInputPeer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsEncryptedChat messagesRequestEncryption(TLAbsInputUser tlAbsInputUser, int i, TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSaveDraft(boolean b, Integer integer, TLAbsInputPeer tlAbsInputPeer, String s, TLVector<TLAbsMessageEntity> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSaveGif(TLAbsInputDocument tlAbsInputDocument, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSaveRecentSticker(boolean b, TLAbsInputDocument tlAbsInputDocument, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsMessages messagesSearch(TLAbsInputPeer tlAbsInputPeer, String s, TLAbsMessagesFilter tlAbsMessagesFilter, int i, int i1, int i2, int i3, int i4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLFoundGifs messagesSearchGifs(String s, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsMessages messagesSearchGlobal(String s, int i, TLAbsInputPeer tlAbsInputPeer, int i1, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsSentEncryptedMessage messagesSendEncrypted(TLInputEncryptedChat tlInputEncryptedChat, long l, TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsSentEncryptedMessage messagesSendEncryptedFile(TLInputEncryptedChat tlInputEncryptedChat, long l, TLBytes tlBytes, TLAbsInputEncryptedFile tlAbsInputEncryptedFile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsSentEncryptedMessage messagesSendEncryptedService(TLInputEncryptedChat tlInputEncryptedChat, long l, TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesSendInlineBotResult(boolean b, boolean b1, boolean b2, TLAbsInputPeer tlAbsInputPeer, Integer integer, long l, long l1, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesSendMedia(boolean b, boolean b1, boolean b2, TLAbsInputPeer tlAbsInputPeer, Integer integer, TLAbsInputMedia tlAbsInputMedia, long l, TLAbsReplyMarkup tlAbsReplyMarkup) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesSendMessage(boolean b, boolean b1, boolean b2, boolean b3, TLAbsInputPeer tlAbsInputPeer, Integer integer, String s, long l, TLAbsReplyMarkup tlAbsReplyMarkup, TLVector<TLAbsMessageEntity> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSetBotCallbackAnswer(boolean b, long l, String s, String s1, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSetBotPrecheckoutResults(boolean b, long l, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSetBotShippingResults(long l, String s, TLVector<TLShippingOption> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSetEncryptedTyping(TLInputEncryptedChat tlInputEncryptedChat, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesSetGameScore(boolean b, boolean b1, TLAbsInputPeer tlAbsInputPeer, int i, TLAbsInputUser tlAbsInputUser, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSetInlineBotResults(boolean b, boolean b1, long l, TLVector<TLAbsInputBotInlineResult> tlVector, int i, String s, TLInlineBotSwitchPM tlInlineBotSwitchPM) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSetInlineGameScore(boolean b, boolean b1, TLInputBotInlineMessageID tlInputBotInlineMessageID, TLAbsInputUser tlAbsInputUser, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesSetTyping(TLAbsInputPeer tlAbsInputPeer, TLAbsSendMessageAction tlAbsSendMessageAction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesStartBot(TLAbsInputUser tlAbsInputUser, TLAbsInputPeer tlAbsInputPeer, long l, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates messagesToggleChatAdmins(int i, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesToggleDialogPin(boolean b, TLAbsInputPeer tlAbsInputPeer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool messagesUninstallStickerSet(TLAbsInputStickerSet tlAbsInputStickerSet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool paymentsClearSavedInfo(boolean b, boolean b1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPaymentForm paymentsGetPaymentForm(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPaymentReceipt paymentsGetPaymentReceipt(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLSavedInfo paymentsGetSavedInfo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsPaymentResult paymentsSendPaymentForm(int i, String s, String s1, TLAbsInputPaymentCredentials tlAbsInputPaymentCredentials) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLValidatedRequestedInfo paymentsValidateRequestedInfo(boolean b, int i, TLPaymentRequestedInfo tlPaymentRequestedInfo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPhoneCall phoneAcceptCall(TLInputPhoneCall tlInputPhoneCall, TLBytes tlBytes, TLPhoneCallProtocol tlPhoneCallProtocol) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPhoneCall phoneConfirmCall(TLInputPhoneCall tlInputPhoneCall, TLBytes tlBytes, long l, TLPhoneCallProtocol tlPhoneCallProtocol) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates phoneDiscardCall(TLInputPhoneCall tlInputPhoneCall, int i, TLAbsPhoneCallDiscardReason tlAbsPhoneCallDiscardReason, long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLDataJSON phoneGetCallConfig() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool phoneReceivedCall(TLInputPhoneCall tlInputPhoneCall) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPhoneCall phoneRequestCall(TLAbsInputUser tlAbsInputUser, int i, TLBytes tlBytes, TLPhoneCallProtocol tlPhoneCallProtocol) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool phoneSaveCallDebug(TLInputPhoneCall tlInputPhoneCall, TLDataJSON tlDataJSON) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUpdates phoneSetCallRating(TLInputPhoneCall tlInputPhoneCall, int i, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLLongVector photosDeletePhotos(TLVector<TLAbsInputPhoto> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsPhotos photosGetUserPhotos(TLAbsInputUser tlAbsInputUser, int i, long l, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsUserProfilePhoto photosUpdateProfilePhoto(TLAbsInputPhoto tlAbsInputPhoto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLPhoto photosUploadProfilePhoto(TLAbsInputFile tlAbsInputFile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsChannelDifference updatesGetChannelDifference(boolean b, TLAbsInputChannel tlAbsInputChannel, TLAbsChannelMessagesFilter tlAbsChannelMessagesFilter, int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsDifference updatesGetDifference(int i, Integer integer, int i1, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLState updatesGetState() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsCdnFile uploadGetCdnFile(TLBytes tlBytes, int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLAbsFile uploadGetFile(TLAbsInputFileLocation tlAbsInputFileLocation, int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLWebFile uploadGetWebFile(TLInputWebFileLocation tlInputWebFileLocation, int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool uploadReuploadCdnFile(TLBytes tlBytes, TLBytes tlBytes1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool uploadSaveBigFilePart(long l, int i, int i1, TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLBool uploadSaveFilePart(long l, int i, TLBytes tlBytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLUserFull usersGetFullUser(TLAbsInputUser tlAbsInputUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TLVector<TLAbsUser> usersGetUsers(TLVector<TLAbsInputUser> tlVector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isClosed() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public TLAbsUpdates messagesSendMessage(TLAbsInputPeer tlAbsInputPeer, String s, long l) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public <T extends TLObject> Observable<T> queueMethod(TLMethod<T> tlMethod, int i, long l, long l1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends TLObject> void queueMethodImmediate(TLMethod<T> tlMethod, long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setExportedClientTimeout(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTimeout(long l) {
        throw new UnsupportedOperationException();
    }
}
