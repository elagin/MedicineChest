package ru.crew4dev.medicinechest.presentation.screens.menu

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import ru.crew4dev.medicinechest.app.navigation.Screens
import ru.crew4dev.medicinechest.app.permission.Permission
import ru.crew4dev.medicinechest.app.permission.PermissionProvider
import ru.crew4dev.medicinechest.domain.model.client.DocumentType
import ru.crew4dev.medicinechest.domain.model.login.CardType
import ru.crew4dev.medicinechest.domain.usecases.*
import ru.crew4dev.medicinechest.presentation.base.viewmodel.BaseViewModel
import ru.crew4dev.medicinechest.presentation.base.viewmodel.onNext
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val router: Router,
    private val permissionProvider: PermissionProvider,
    private val appUseCase: AppUseCase,
    private val historyUseCase: HistoryUseCase,
    private val clientUseCase: ClientUseCase,
    private val opsUseCase: OpsUseCase,
    private val mcbUseCase: McbUseCase,
    private val mcbCreditUseCase: McbCreditUseCase,
    private val dvbUseCase: DvbUseCase,
    private val context: Context?
) : BaseViewModel() {

    val cardType = MutableLiveData<CardType>()

    override fun onBackPressed() = router.finishChain()

    fun onAfterInit(fragment: Fragment, context: Context?) {
        clientUseCase.deleteAll()
        mcbUseCase.deleteAll()
        mcbCreditUseCase.deleteAll()
        dvbUseCase.deleteAll()

        requestCardType()

        if (!permissionProvider.isGranted(Permission.POSITION)) {
            permissionProvider.request(fragment, Permission.POSITION)
        }
        runWorkTask(context ?: return)
    }

    fun onVskPressed() = router.navigateTo(Screens.SearchSectionScreen(DocumentType.VSK))

    fun onMcbDebitPressed() {
        historyUseCase.initProcess()
        clientUseCase.saveCurrentDocumentType(DocumentType.MCB)
        router.navigateTo(Screens.SearchSectionScreen(DocumentType.MCB))
    }

    fun onMcbFamilyTeamPressed() {
        historyUseCase.initProcess()
        clientUseCase.saveCurrentDocumentType(DocumentType.MCB_FAMILY)
        router.navigateTo(Screens.SearchSectionScreen(DocumentType.MCB_FAMILY))
    }

    fun onMcbCreditPressed() {
        clientUseCase.saveCurrentDocumentType(DocumentType.MCB_CREDIT)
        router.navigateTo(Screens.CheckMcbCreditScreen())
    }

    fun onMcbLoanPressed() {
        clientUseCase.saveCurrentDocumentType(DocumentType.MCB_LOAN)
        router.navigateTo(Screens.CheckMcbCreditScreen())
    }

    fun onDvbIssuePressed() {
        historyUseCase.initProcess()
        router.navigateTo(Screens.SearchSectionScreen(DocumentType.DVB))
    }

    fun onHypothecIssuePressed() {
        historyUseCase.initProcess()
        router.navigateTo(Screens.CheckHypothecScreen(null))
    }

    fun onOpsPressed() = router.navigateTo(opsUseCase.getFirstOpsScreen())
    fun onWpPressed() = router.navigateTo(Screens.WpInviteScreen(null))
    fun onIpoPressed() = router.navigateTo(Screens.CheckIpoScreen())

    fun onRequestPermissionsResult(context: Context?, requestCode: Int, grantResults: IntArray) {
        context ?: return
        if (permissionProvider.isGrantedAfterRequest(Permission.POSITION, requestCode, grantResults)) {
            runWorkTask(context)
        }
    }

    private fun runWorkTask(context: Context) {
//        Log.d(GeoWorker.TAG, "clientUseCase.isRunGeoTask() = " + clientUseCase.isRunGeoTask())
//        if (clientUseCase.isRunGeoTask())
//            return
//        clientUseCase.setGeoTask(true)
        //runWork(context)
    }


    private fun requestCardType() {
        safeSubscribe {
            appUseCase.getUserCardType()
                .subscribe(
                    { cardType.onNext(it) },
                    ::handleError
                )
        }
    }
}