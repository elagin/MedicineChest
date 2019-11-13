package ru.crew4dev.medicinechest.presentation.base.error

import ru.crew4dev.medicinechest.R
import ru.crew4dev.medicinechest.domain.exceptions.*

object ErrorMapper {

    fun map(th: Throwable): Int = when (th) {
        is UserNotFoundException -> R.string.login_send_sms_error
        is SmsNotRequestedException -> R.string.vsk_create_error
        is WrongConfirmCodeException -> R.string.login_confirm_error
        is DocumentNotFoundException -> R.string.document_error
        is OpsStatusException -> R.string.error_server
        is MissingPartException -> R.string.client_card_ops_status_error
        is WrongSnilsNumberException -> R.string.check_agreement_error
        is WrongDocsCountException -> R.string.get_scan_agreement_error
        is BadDataException -> R.string.create_agreement_error
        is ServiceUnavailableException -> R.string.service_unavailable_error
        is InvalidPushException -> R.string.error_server
        is AuthException -> R.string.error_auth
        is ServerException -> R.string.error_server
        is TimeoutException -> R.string.error_timeout
        is NetworkException -> R.string.error_network
        else -> throw th
    }

}