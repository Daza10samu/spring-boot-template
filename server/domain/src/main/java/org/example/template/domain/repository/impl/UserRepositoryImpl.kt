package org.example.template.domain.repository.impl

import org.example.template.domain.db.Tables.USERS
import org.example.template.domain.db.tables.records.UsersRecord
import org.example.template.domain.model.User
import org.example.template.domain.repository.UserRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.support.TransactionTemplate

@Repository
class UserRepositoryImpl(
    private val dsl: DSLContext,
    private val transactionTemplate: TransactionTemplate,
) : UserRepository {
    override fun get(id: Long, forUpdate: Boolean): User? {
        return dsl.selectFrom(USERS)
            .where(USERS.ID.eq(id))
            .let {
                if (forUpdate) {
                    it.forUpdate()
                } else {
                    it
                }
            }
            .fetchOne()?.toModel()
    }

    override fun findByUsername(username: String): User? {
        return dsl.selectFrom(USERS)
            .where(USERS.USERNAME.eq(username))
            .fetchOne()?.toModel()
    }

    override fun saveUser(user: User) {
        dsl.insertInto(USERS)
            .set(user.toRecord().apply { changed(USERS.ID, false) })
            .execute()
    }

    override fun updateUser(id: Long, user: User) {
        transactionTemplate.execute {
            val userOld = get(id, forUpdate = true)

            validateUpdate(user, userOld)

            dsl.update(USERS)
                .set(user.copy(id = id).toRecord())
                .where(USERS.ID.eq(id))
        }
    }

    private fun validateUpdate(user: User, userOld: User?) {
        if (userOld == null) throw IllegalStateException("No such user")

        requireNotNull(user.username == userOld.username)
        requireNotNull(user.id == userOld.id)
    }

    companion object {
        private fun User.toRecord(): UsersRecord = UsersRecord(
            id,
            username,
            password,
        )

        private fun UsersRecord.toModel(): User = User(
            id,
            username,
            password,
        )
    }
}
