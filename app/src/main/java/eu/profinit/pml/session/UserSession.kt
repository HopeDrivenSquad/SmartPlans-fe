package eu.profinit.pml.session

object  UserSession {
    var clientId: String? = null

    fun start(clientId: String) {
        this.clientId = clientId
    }
}