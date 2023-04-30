package bstrees.presenter

import bstrees.model.dataBases.reps.JsonTreeRepo
import bstrees.model.dataBases.reps.Neo4jTreeRepo
import bstrees.model.dataBases.reps.SQLTreeRepo

object DataBasePresenter {
    fun connectNeo4j(host: String, username: String, password: String): TreePresenter{
        return TreePresenter(Neo4jTreeRepo(host, username, password))
    }

    fun connectSQL(bdName: String): TreePresenter{
        return TreePresenter(SQLTreeRepo(bdName))
    }

    fun connectJson(dirName: String): TreePresenter{
        return TreePresenter(JsonTreeRepo(dirName))
    }
}