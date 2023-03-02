package models

import org.joda.time.DateTime

final case class EventInfo (id: Long,
                            profileId: Long,
                            eventType: String,
                            invokedAt: DateTime)
