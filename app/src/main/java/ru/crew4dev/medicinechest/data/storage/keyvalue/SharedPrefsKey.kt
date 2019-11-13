package ru.crew4dev.medicinechest.data.storage.keyvalue

import ru.crew4dev.medicinechest.domain.storage.keyvalue.Key

sealed class SharedPrefsKey(val name: String) : Key {
    object AuthData : SharedPrefsKey("AuthData")
    object VersionCode: SharedPrefsKey("versionCode")
    object UserId : SharedPrefsKey("userId")

    object Client : SharedPrefsKey("client")
    object ClientId : SharedPrefsKey("clientId")
    object DocumentType : SharedPrefsKey("DocumentType")
    object SmsCode : SharedPrefsKey("SmsCode")

    object HistoryCurrentKey : SharedPrefsKey("HistoryCurrentKey")
    object History : SharedPrefsKey("History")
    class HistoryMcbData(currentKey: Long) : SharedPrefsKey("HistoryMcbData|$currentKey")
    class HistoryDvbData(currentKey: Long) : SharedPrefsKey("HistoryDvbData|$currentKey")

    object McbCreateCard : SharedPrefsKey("McbCreateCard")
    object McbActivateCard : SharedPrefsKey("McbActivateCard")
    object McbUploadStatus : SharedPrefsKey("McbUploadStatus")
    object McbOperationId : SharedPrefsKey("McbOperationId")
    object McbSignedDocsStatus : SharedPrefsKey("McbSignedDocsStatus")
    object McbClientPhone : SharedPrefsKey("McbClientPhone")
    object McbPassportRecognition : SharedPrefsKey("McbPassportRecognition")
    object McbPassportOldRecognition : SharedPrefsKey("McbPassportOldRecognition")
    object McbRegistrationRecognition : SharedPrefsKey("McbRegistrationRecognition")
    object McbPassport: SharedPrefsKey("McbPassport")

    object McbCreditOperationId : SharedPrefsKey("McbCreditOperationId")
    object McbCreditLimitData : SharedPrefsKey("McbCreditLimitData")
    object McbLoanLimitData : SharedPrefsKey("McbLoanLimitData")
    object McbCreditPrimaryInfoData : SharedPrefsKey("McbCreditPrimaryInfoData")
    object McbCreditFullname : SharedPrefsKey("McbCreditFullname")
    object McbEmploymentStatus : SharedPrefsKey("McbEmploymentStatus")
    object McbAdditionalInfo : SharedPrefsKey("McbAdditionalInfo")
    object McbFullJobInfo : SharedPrefsKey("McbFullJobInfo")

    object OpsStartTime : SharedPrefsKey("OpsStartTime")
    object Snils : SharedPrefsKey("snils")
    object NpfStatus : SharedPrefsKey("npfStatus")
    object OperationId : SharedPrefsKey("operationId")

    object IpoOperationId : SharedPrefsKey("IpoOperationId")
    object IpoSecondaryData : SharedPrefsKey("IpoSecondaryData")
    object IpoSmsCode : SharedPrefsKey("IpoSmsCode")

    object DvbClientAddress : SharedPrefsKey("clientIndex")
    object DvbCreateCard : SharedPrefsKey("dvbCreateCard")
    object DvbPassport: SharedPrefsKey("dvbPassport")
    object DvbPassportRecognition: SharedPrefsKey("DvbPassportRecognition")
    object DvbRegistrationRecognition: SharedPrefsKey("DvbRegistrationRecognition")
    object DvbOperationId: SharedPrefsKey("dvbCreatingStatus")
    object DvbPushData: SharedPrefsKey("DvbPushData")
    object McbPushData: SharedPrefsKey("McbPushData")
    object HasBeneficiary : SharedPrefsKey("hasBeneficiary")
    object HasResidence : SharedPrefsKey("hasResidence")
    object HasInsurance : SharedPrefsKey("hasInsurance")

    object RunGeoTask : SharedPrefsKey("RunGeoTask")
}