package com.umasuo.developer.domain.service

import com.umasuo.developer.domain.model.Developer
import com.umasuo.developer.infrastructure.repository.DeveloperRepository
import com.umasuo.developer.infrastructure.util.PasswordUtil
import spock.lang.Specification

/**
 * Created by umasuo on 17/3/7.
 */
class DeveloperServiceTest extends Specification {

    DeveloperService developerService

    DeveloperRepository developerRepository

    def id = "QWERTYUIOPASDFGHJ"

    def email = "test@umasuo.com"

    def rawPassword = "password"

    def hashedPassword = PasswordUtil.hashPassword(rawPassword)

    def setup() {
        developerRepository = Mock(DeveloperRepository)
        developerService = new DeveloperService(repository: developerRepository)
    }

    def "Test 1.1: create developer with sample"() {
        Developer developer = new Developer(id: id, email: email, password: hashedPassword)
        when:
        developerRepository.save(_) >> developer
        def result = developerService.create(developer)
        then:
        noExceptionThrown()
        result.getId() == developer.getId()
    }
}
