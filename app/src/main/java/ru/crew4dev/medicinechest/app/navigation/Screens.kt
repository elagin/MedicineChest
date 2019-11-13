package ru.crew4dev.medicinechest.app.navigation

import androidx.fragment.app.Fragment
import ru.crew4dev.medicinechest.domain.model.client.Client
import ru.crew4dev.medicinechest.domain.model.client.DocumentType
import ru.crew4dev.medicinechest.domain.model.client.document.Pdf
import ru.crew4dev.medicinechest.domain.model.client.document.SignedDoc
import ru.crew4dev.medicinechest.domain.model.client.photo.DocumentPhotoPackage
import ru.crew4dev.medicinechest.domain.model.dvb.DvbClientAddress
import ru.crew4dev.medicinechest.domain.model.dvb.DvbCreateCard
import ru.crew4dev.medicinechest.domain.model.mcb.debit.McbCreateCard
import ru.crew4dev.medicinechest.domain.model.vsk.PolicyType
import ru.crew4dev.medicinechest.presentation.screens.camera.CameraFragment
import ru.crew4dev.medicinechest.presentation.screens.client.ClientCardFragment
import ru.crew4dev.medicinechest.presentation.screens.client.documents.detail.pdf.PdfDetailFragment
import ru.crew4dev.medicinechest.presentation.screens.client.documents.detail.photo.PhotoDetailFragment
import ru.crew4dev.medicinechest.presentation.screens.client.documents.list.DocumentListFragment
import ru.crew4dev.medicinechest.presentation.screens.client.documents.list.DocumentListType
import ru.crew4dev.medicinechest.presentation.screens.client.edit.ClientEditFragment
import ru.crew4dev.medicinechest.presentation.screens.client.success.SentSuccessFragment
import ru.crew4dev.medicinechest.presentation.screens.confirm.ConfirmFragment
import ru.crew4dev.medicinechest.presentation.screens.confirm.ConfirmType
import ru.crew4dev.medicinechest.presentation.screens.history.HistoryFragment
//import ru.crew4dev.medicinechest.presentation.screens.login.phone.LoginPhoneFragment
//import ru.crew4dev.medicinechest.presentation.screens.login.sms.LoginSmsFragment
import ru.crew4dev.medicinechest.presentation.screens.menu.MenuFragment
import ru.crew4dev.medicinechest.presentation.screens.search.SearchSectionFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.address.AddressDvbFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.card.DvbCreateCardFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.preview.DvbContractReviewArgs
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.preview.DvbContractReviewFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.status.StatusDvbFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.success.SuccessDvbFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ipo.check.CheckIpoFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ipo.review.ReviewIpoFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ipo.send.SendIpoFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ipo.success.SuccessIpoFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.employment.EmploymentMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.fullname.FullnameMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.info.AdditionalInfoMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.job.JobMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.send.SendAdditionalMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.docs.error.McbCreditErrorFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.docs.kitdocuments.McbCreditKitDocsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.docs.sendsigndocs.SendSignDocsMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.check.CheckMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.info.PrimaryInfoMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.limit.LimitMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.limit.LimitMcbLoanFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.send.SendPrimaryMcbCreditFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.сardNumber.GetCardNumberFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.create.CreateMcbFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.pin.McbPinArgs
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.pin.PinMcbFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.preview.McbReviewArgs
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.preview.ReviewMcbFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.status.StatusMcbFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.success.SuccessMcbFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.upload.UploadFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.hypothec.CheckHypothecFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.cap.CapReviewOpsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.cap.CapWaitOpsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.contract.ContractReviewOpsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.contract.ContractWaitOpsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.create.fullname.FullnameFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.create.passport.PassportFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.error.ErrorAccessDeniedFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.lossWarning.LossWarningFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.signed.SignedWaitOpsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.signer.SignerOpsDrawerFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.signer.SignerOpsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.snils.SnilsCheckOpsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.snils.SnilsExistsOpsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.snils.SnilsInputOpsFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.vsk.create.CreateVskFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.vsk.payment.PaymentVskFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.vsk.success.SuccessVskFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.vsk.view.ViewVskFragment
import ru.crew4dev.medicinechest.presentation.screens.sections.wp.WpInviteScreenFragment
import ru.crew4dev.medicinechest.presentation.screens.splash.SplashFragment
import ru.crew4dev.medicinechest.presentation.screens.statistics.StatisticsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object SplashScreen : SupportAppScreen() {
        override fun getFragment() = SplashFragment()
    }

    //Login
/*
    class LoginPhoneScreen(private val afterLogout: Boolean) : SupportAppScreen() {
        override fun getFragment() = LoginPhoneFragment.newInstance(afterLogout)
    }

    class LoginSmsScreen(private val phoneNumber: String) : SupportAppScreen() {
        override fun getFragment() = LoginSmsFragment.newInstance(phoneNumber)
    }
*/
    //Main

    class MenuScreen : SupportAppScreen() {
        override fun getFragment() = MenuFragment()
    }
/*
    object HistoryScreen : SupportAppScreen() {
        override fun getFragment() = HistoryFragment()
    }

    object StatisticsScreen : SupportAppScreen() {
        override fun getFragment() = StatisticsFragment()
    }

    class CameraScreen(private val documentPhotoPackage: DocumentPhotoPackage) : SupportAppScreen() {
        override fun getFragment() = CameraFragment.newInstance(documentPhotoPackage)
    }

    class ConfirmScreen(
        private val type: ConfirmType,
        private val phoneNumber: String? = null,
        private val documentType: DocumentType? = null
    ) : SupportAppScreen() {
        override fun getFragment() = ConfirmFragment.newInstance(type, phoneNumber, documentType)
    }

    class SearchSectionScreen(private val documentType: DocumentType) : SupportAppScreen() {
        override fun getFragment() = SearchSectionFragment.newInstance(documentType)
    }

    //Client

    class ClientCardScreen(
        private val clientId: Long,
        private val issueCard: Boolean = false
    ) : SupportAppScreen() {
        override fun getFragment() = ClientCardFragment.newInstance(clientId, issueCard)
    }

    class ClientEditScreen(private val client: Client) : SupportAppScreen() {
        override fun getFragment() = ClientEditFragment.newInstance(client)
    }

    class DocumentListScreen(
        private val documentListType: DocumentListType,
        private val operationId: Long = 0
    ) : SupportAppScreen() {
        override fun getFragment() = DocumentListFragment.newInstance(documentListType, operationId)
    }

    class PdfDetailScreen(private val pdf: Pdf) : SupportAppScreen() {
        override fun getFragment() = PdfDetailFragment.newInstance(pdf)
    }

    class PhotoDetailScreen(private val signedDoc: SignedDoc) : SupportAppScreen() {
        override fun getFragment() = PhotoDetailFragment.newInstance(signedDoc)
    }

    class SentSuccessScreen(private val resId: Int, private val isNeedBack: Boolean) : SupportAppScreen() {
        override fun getFragment() = SentSuccessFragment.newInstance(resId, isNeedBack)
    }

    //VSK

    class CreateVskScreen(private val clientId: Long?) : SupportAppScreen() {
        override fun getFragment() = CreateVskFragment.newInstance(clientId)
    }

    class ViewVskScreen(
        private val isNew: Boolean,
        private val client: Client,
        private val policyId: Long? = null,
        private val policyType: PolicyType? = null
    ) : SupportAppScreen() {
        override fun getFragment() = ViewVskFragment.newInstance(isNew, client, policyId, policyType)
    }

    class PaymentVskScreen(
        private val client: Client,
        private val policyType: PolicyType,
        private val clientId: Long,
        private val operationId: Long
    ) : SupportAppScreen() {
        override fun getFragment() = PaymentVskFragment.newInstance(client, policyType, clientId, operationId)
    }

    class SuccessVskScreen(private val policyId: Long) : SupportAppScreen() {
        override fun getFragment() = SuccessVskFragment.newInstance(policyId)
    }

    //MCB DEBIT

    class CreateMcbScreen(
        private val clientId: Long?,
        private val mcbCreateCard: McbCreateCard? = null
    ) : SupportAppScreen() {
        override fun getFragment() = CreateMcbFragment.newInstance(clientId, mcbCreateCard)
    }

    object StatusMcbScreen : SupportAppScreen() {
        override fun getFragment() = StatusMcbFragment()
    }

    object UploadScreen : SupportAppScreen() {
        override fun getFragment() = UploadFragment()
    }

    class ReviewMcbScreen(private val args: McbReviewArgs) : SupportAppScreen() {
        override fun getFragment() = ReviewMcbFragment.newInstance(args)
    }

    class GetCardNumberScreen(
        private val clientId: Long,
        private val phoneNumber: String,
        private val fromClientCard: Boolean
    ) : SupportAppScreen() {
        override fun getFragment() = GetCardNumberFragment.newInstance(clientId, phoneNumber, fromClientCard)
    }

    class PinMcbScreen(private val args: McbPinArgs) : SupportAppScreen() {
        override fun getFragment() = PinMcbFragment.newInstance(args)
    }

    class SuccessMcbScreen(private val documentType: DocumentType?) : SupportAppScreen() {
        override fun getFragment() = SuccessMcbFragment.newInstance(documentType)
    }

    //todo прикрутить
    class ContractReviewMcbScreen(private val args: DvbContractReviewArgs) : SupportAppScreen() {
        //override fun getFragment(): Fragment = ContractReviewMcbFragment.newInstance(args)
    }

    class CheckHypothecScreen(
        private val clientId: Long?
    ) : SupportAppScreen() {
        override fun getFragment() = CheckHypothecFragment.newInstance(clientId)
    }

    //MCB CREDIT

    class CheckMcbCreditScreen : SupportAppScreen() {
        override fun getFragment() = CheckMcbCreditFragment()
    }

    class LimitMcbCreditScreen : SupportAppScreen() {
        override fun getFragment() = LimitMcbCreditFragment()
    }

    class LimitMcbLoanScreen : SupportAppScreen() {
        override fun getFragment() = LimitMcbLoanFragment()
    }

    class PrimaryInfoMcbCreditScreen : SupportAppScreen() {
        override fun getFragment() = PrimaryInfoMcbCreditFragment()
    }

    class SendPrimaryMcbCreditScreen : SupportAppScreen() {
        override fun getFragment() = SendPrimaryMcbCreditFragment()
    }

    class FullnameMcbCreditScreen : SupportAppScreen() {
        override fun getFragment() = FullnameMcbCreditFragment()
    }

    class EmploymentMcbCreditScreen : SupportAppScreen() {
        override fun getFragment() = EmploymentMcbCreditFragment()
    }

    class AdditionalInfoMcbCreditScreen : SupportAppScreen() {
        override fun getFragment() = AdditionalInfoMcbCreditFragment()
    }

    class JobMcbCreditScreen : SupportAppScreen() {
        override fun getFragment() = JobMcbCreditFragment()
    }

    class SendAdditionalCreditScreen(private val isNeedRetry: Boolean = false) : SupportAppScreen() {
        override fun getFragment() = SendAdditionalMcbCreditFragment.newInstance(isNeedRetry)
    }

    class McbCreditKitDocsScreen(
        private val clientId: Long,
        private val operationId: Long
    ) : SupportAppScreen() {
        override fun getFragment() = McbCreditKitDocsFragment.newInstance(
            clientId = clientId,
            operationId = operationId
        )
    }

    class McbCreditErrorScreen(private val clientId: Long) : SupportAppScreen() {
        override fun getFragment(): Fragment = McbCreditErrorFragment.newInstance(clientId)
    }

    class SendSignDocsMcbCreditScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = SendSignDocsMcbCreditFragment()
    }

    //OPS

    object SignerOpsDrawerScreen : SupportAppScreen() {
        override fun getFragment() = SignerOpsDrawerFragment()
    }

    object SignerOpsScreen : SupportAppScreen() {
        override fun getFragment() = SignerOpsFragment()
    }

    object SnilsInputOpsScreen : SupportAppScreen() {
        override fun getFragment() = SnilsInputOpsFragment()
    }

    class SnilsCheckOpsScreen(private val snilsNumber: String) : SupportAppScreen() {
        override fun getFragment() = SnilsCheckOpsFragment.newInstance(snilsNumber)
    }

    object SnilsExistsOpsScreen : SupportAppScreen() {
        override fun getFragment() = SnilsExistsOpsFragment()
    }

    class FullnameScreen(
        private val clientId: Long?,
        private val documentType: DocumentType,
        private val isFromCache: Boolean = false
    ) : SupportAppScreen() {
        override fun getFragment() = FullnameFragment.newInstance(clientId, documentType, isFromCache)
    }

    class PassportScreen(
        private val client: Client?,
        private val documentType: DocumentType,
        private val isFromCache: Boolean = false
    ) : SupportAppScreen() {
        override fun getFragment() = PassportFragment.newInstance(client, documentType, isFromCache)
    }

    class ContractWaitOpsScreen : SupportAppScreen() {
        override fun getFragment() = ContractWaitOpsFragment()
    }

    object ContractReviewOpsScreen : SupportAppScreen() {
        override fun getFragment() = ContractReviewOpsFragment()
    }

    object CapWaitOpsScreen : SupportAppScreen() {
        override fun getFragment() = CapWaitOpsFragment()
    }

    object CapReviewOpsScreen : SupportAppScreen() {
        override fun getFragment() = CapReviewOpsFragment()
    }

    object SignedWaitOpsScreen : SupportAppScreen() {
        override fun getFragment() = SignedWaitOpsFragment()
    }

    object LossWarningOpsScreen : SupportAppScreen() {
        override fun getFragment() = LossWarningFragment()
    }

    class ErrorAccessDeniedScreen(private val documentType: DocumentType) : SupportAppScreen() {
        override fun getFragment(): Fragment = ErrorAccessDeniedFragment.newInstance(documentType)
    }

    // IPO

    class CheckIpoScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = CheckIpoFragment()
    }

    class SendIpoScreen : SupportAppScreen() {
        override fun getFragment() = SendIpoFragment()
    }

    class ReviewIpoScreen(
        private val isConfirmed: Boolean = true,
        private val isPaid: Boolean = true
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ReviewIpoFragment.newInstance(
            isConfirmed = isConfirmed,
            isPaid = isPaid
        )
    }

    class SuccessIpoScreen : SupportAppScreen() {
        override fun getFragment() = SuccessIpoFragment()
    }

    //DVB

    class AddressDvbScreen(private val dvbClientAddress: DvbClientAddress? = null) : SupportAppScreen() {
        override fun getFragment(): Fragment = AddressDvbFragment.newInstance(dvbClientAddress)
    }


    class DvbCreateCardScreen(private val dvbCreateCard: DvbCreateCard? = null) : SupportAppScreen() {
        override fun getFragment(): Fragment = DvbCreateCardFragment.newInstance(dvbCreateCard)
    }

    object StatusDvbScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = StatusDvbFragment()
    }

    class DvbContractReviewScreen(private val args: DvbContractReviewArgs) : SupportAppScreen() {
        override fun getFragment(): Fragment = DvbContractReviewFragment.newInstance(args)
    }

    object SuccessDvbScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = SuccessDvbFragment()
    }

    class WpInviteScreen(
        private val clientPhone: String?
    ) : SupportAppScreen() {
        override fun getFragment() = WpInviteScreenFragment.newInstance(clientPhone)
    }
 */
}