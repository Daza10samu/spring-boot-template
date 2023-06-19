package org.example.template.application.service

import org.jobrunr.jobs.annotations.Job
import org.jobrunr.scheduling.JobScheduler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class JobRunrTest(
    private val jobScheduler: JobScheduler
) {
    init {
        LOG.info("Initing")
        jobScheduler.enqueue {
            helloWorldJob("enqueued")
        }
        jobScheduler.scheduleRecurrently(Duration.ofMinutes(5)) {
            helloWorldJob("recurrently")
        }
    }

    @Job(name = "hello wotld job", retries = 3)
    fun helloWorldJob(startSource: String) {
        LOG.info("Hello world. [$startSource]")
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(JobRunrTest::class.java)
    }
}