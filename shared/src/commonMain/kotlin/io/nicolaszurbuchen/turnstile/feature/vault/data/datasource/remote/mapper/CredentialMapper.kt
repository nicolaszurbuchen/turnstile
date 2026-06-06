package io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.mapper

import io.nicolaszurbuchen.turnstile.feature.vault.data.datasource.remote.dto.CredentialDto
import io.nicolaszurbuchen.turnstile.feature.vault.domain.model.Credential

class CredentialMapper {
    fun toDomain(dto: CredentialDto): Credential =
        Credential(
            id = dto.id,
            title = dto.title,
            username = dto.username,
            password = dto.password,
            memo = dto.memo,
        )

    fun toDto(domain: Credential): CredentialDto =
        CredentialDto(
            id = domain.id,
            title = domain.title,
            username = domain.username,
            password = domain.password,
            memo = domain.memo,
        )
}
