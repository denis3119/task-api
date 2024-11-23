package my.denis3119.task_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCaching
@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
class TaskApiApplication

fun main(args: Array<String>) {
    runApplication<TaskApiApplication>(*args)
}
