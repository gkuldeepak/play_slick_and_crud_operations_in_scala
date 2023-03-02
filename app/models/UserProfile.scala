package models

import org.joda.time.DateTime

final case class UserProfile (id: Long, firstName: String, lastName: String,
                              email: String, department: String, created: DateTime = new DateTime(), status:Boolean = true)
