package eu.profinit.pml.session

object  UserSession {
    var userName: String? = null

    fun start(userName: String) {
        this.userName = userName
    }
}