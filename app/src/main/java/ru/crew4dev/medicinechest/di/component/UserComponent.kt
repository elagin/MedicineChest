package ru.crew4dev.medicinechest.di.component

import dagger.Subcomponent
import ru.crew4dev.medicinechest.app.geotask.GeoWorker
import ru.crew4dev.medicinechest.di.module.user.*
import ru.crew4dev.medicinechest.di.scope.UserScope
import ru.crew4dev.medicinechest.presentation.screens.camera.CameraViewModel
import ru.crew4dev.medicinechest.presentation.screens.client.ClientCardViewModel
import ru.crew4dev.medicinechest.presentation.screens.client.documents.detail.pdf.PdfDetailViewModel
import ru.crew4dev.medicinechest.presentation.screens.client.documents.detail.photo.PhotoDetailViewModel
import ru.crew4dev.medicinechest.presentation.screens.client.documents.list.DocumentListViewModel
import ru.crew4dev.medicinechest.presentation.screens.client.edit.ClientEditViewModel
import ru.crew4dev.medicinechest.presentation.screens.client.success.SentSuccessViewModel
import ru.crew4dev.medicinechest.presentation.screens.confirm.ConfirmViewModel
import ru.crew4dev.medicinechest.presentation.screens.history.HistoryViewModel
import ru.crew4dev.medicinechest.presentation.screens.menu.MenuViewModel
import ru.crew4dev.medicinechest.presentation.screens.search.SearchViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.address.AddressDvbViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.card.DvbCreateCardViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.preview.DvbContractReviewViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.status.StatusDvbViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.dvb.success.SuccessDvbViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ipo.check.CheckIpoViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ipo.review.ReviewIpoViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ipo.send.SendIpoViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ipo.success.SuccessIpoViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.employment.EmploymentMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.fullname.FullnameMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.info.AdditionalInfoMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.job.JobMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.additional.send.SendAdditionalMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.docs.error.McbCreditErrorViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.docs.kitdocuments.McbCreditKitDocsViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.docs.sendsigndocs.SendSignDocsMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.check.CheckMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.info.PrimaryInfoMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.limit.LimitMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.limit.LimitMcbLoanViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.primary.send.SendPrimaryMcbCreditViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.credit.сardNumber.GetCardNumberViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.create.CreateMcbViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.pin.PinMcbViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.preview.ReviewMcbViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.status.StatusMcbViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.success.SuccessMcbViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.debit.upload.UploadViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.mcb.hypothec.CheckHypothecViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.cap.CapReviewOpsViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.cap.CapWaitOpsViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.contract.ContractReviewOpsViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.contract.ContractWaitOpsViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.create.fullname.FullnameViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.create.passport.PassportViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.error.ErrorAccessDeniedViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.lossWarning.LossWarningViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.signed.SignedWaitOpsViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.signer.SignerOpsViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.snils.SnilsCheckOpsViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.ops.snils.SnilsInputOpsViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.vsk.create.CreateVskViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.vsk.payment.PaymentVskViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.vsk.success.SuccessVskViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.vsk.view.ViewVskViewModel
import ru.crew4dev.medicinechest.presentation.screens.sections.wp.WpInviteViewModel
import ru.crew4dev.medicinechest.presentation.screens.statistics.StatisticsViewModel

@UserScope
@Subcomponent(
    modules = [/*
        UserModule::class,
        ClientModule::class,
        AddressModule::class,
        VskModule::class,
        OpsModule::class,
        IpoModule::class,
        McbModule::class,
        McbCreditModule::class,
        DvbModule::class,
        HistoryModule::class,
        StatisticsModule::class*/
    ]
)
interface UserComponent {


    fun inject(geo: GeoWorker)

    //main

    fun menuViewModel(): MenuViewModel
    /*
    fun historyViewModel(): HistoryViewModel
    fun statisticsViewModel(): StatisticsViewModel
    fun searchViewModel(): SearchViewModel
    fun cameraViewModel(): CameraViewModel
    fun confirmViewModel(): ConfirmViewModel

    //client

    fun clientCardViewModel(): ClientCardViewModel
    fun clientEditViewModel(): ClientEditViewModel
    fun documentListViewModel(): DocumentListViewModel
    fun pdfDetailViewModel(): PdfDetailViewModel
    fun photoDetailViewModel(): PhotoDetailViewModel
    fun fullnameViewModel(): FullnameViewModel
    fun passportViewModel(): PassportViewModel
    fun sentSuccessViewModel(): SentSuccessViewModel

    //vsk

    fun createVskViewModel(): CreateVskViewModel
    fun viewVskViewModel(): ViewVskViewModel
    fun paymentVskViewModel(): PaymentVskViewModel
    fun successVskViewModel(): SuccessVskViewModel

    //mcb debit

    fun createMcbViewModel(): CreateMcbViewModel
    fun statusMcbViewModel(): StatusMcbViewModel
    fun uploadViewModel(): UploadViewModel
    fun pinMcbViewModel(): PinMcbViewModel
    fun successMcbViewModel(): SuccessMcbViewModel
    fun reviewMcbViewModel(): ReviewMcbViewModel

    //mcb credit

    fun checkMcbCreditViewModel(): CheckMcbCreditViewModel
    fun limitMcbCreditViewModel(): LimitMcbCreditViewModel
    fun limitMcbLoanViewModel(): LimitMcbLoanViewModel
    fun primaryInfoMcbCreditViewModel(): PrimaryInfoMcbCreditViewModel
    fun sendPrimaryMcbCreditViewModel(): SendPrimaryMcbCreditViewModel
    fun fullnameMcbCreditViewModel(): FullnameMcbCreditViewModel
    fun employmentMcbCreditViewModel(): EmploymentMcbCreditViewModel
    fun additionalInfoMcbCreditViewModel(): AdditionalInfoMcbCreditViewModel
    fun jobMcbCreditViewModel(): JobMcbCreditViewModel
    fun sendAdditionalMcbCreditViewModel(): SendAdditionalMcbCreditViewModel
    fun sendSignDocsMcbCreditViewModel(): SendSignDocsMcbCreditViewModel
    fun mcbCreditKitDocsViewModel(): McbCreditKitDocsViewModel
    fun mcbCreditErrorViewModel(): McbCreditErrorViewModel
    fun getCardNumberViewModel(): GetCardNumberViewModel

    //ops

    fun signerOpsViewModel(): SignerOpsViewModel
    fun snilsInputOpsViewModel(): SnilsInputOpsViewModel
    fun snilsCheckOpsViewModel(): SnilsCheckOpsViewModel
    fun contractWaitOpsViewModel(): ContractWaitOpsViewModel
    fun contractReviewOpsViewModel(): ContractReviewOpsViewModel
    fun capWaitOpsViewModel(): CapWaitOpsViewModel
    fun capReviewOpsViewModel(): CapReviewOpsViewModel
    fun signedWaitOpsViewModel(): SignedWaitOpsViewModel
    fun lossWarningViewModel(): LossWarningViewModel
    fun errorAccessDeniedViewModel(): ErrorAccessDeniedViewModel

    // ipo

    fun checkIpoViewModel(): CheckIpoViewModel
    fun sendIpoViewModel(): SendIpoViewModel
    fun reviewIpoViewModel(): ReviewIpoViewModel
    fun successIpoViewModel(): SuccessIpoViewModel

    //dvb

    fun statusDvbViewModel(): StatusDvbViewModel
    fun addressDvbViewModel(): AddressDvbViewModel
    fun createCardDvbViewModel(): DvbCreateCardViewModel
    fun contractReviewDvbViewModel(): DvbContractReviewViewModel
    fun successDvbViewModel(): SuccessDvbViewModel

    // hypothec
    fun checkHypothecViewModel(): CheckHypothecViewModel

    // wp
    fun wpInviteViewModel(): WpInviteViewModel
*/
    @Subcomponent.Builder
    interface Builder {
        fun build(): UserComponent
    }
}