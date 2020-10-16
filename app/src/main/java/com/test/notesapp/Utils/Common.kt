package com.test.notesapp.Utils

import android.content.Context
import com.test.notesapp.R
import java.util.*

class Common {

    companion object {

        fun currentDate(): Date {
            val calendar = Calendar.getInstance()
            return calendar.time
        }

        private fun getTimeDistanceInMinutes(time: Long): Int {
            val timeDistance = currentDate().time - time
            return Math.round((Math.abs(timeDistance) / 1000 / 60).toFloat())
        }


        @JvmStatic
        fun getTimeAgo(time: Long, ctx: Context): String? {


            val curDate = currentDate()
            val now = curDate.time
            if (time > now || time <= 0) {
                return null
            }

            val dim = getTimeDistanceInMinutes(time)

            var timeAgo: String? = null

            if (dim == 0) {
                timeAgo = "Just Now"
            } else if (dim == 1) {
                return "1 " + ctx.resources.getString(R.string.date_util_unit_minute)
            } else if (dim >= 2 && dim <= 44) {
                timeAgo =
                    dim.toString() + " " + ctx.resources.getString(R.string.date_util_unit_minutes)
            } else if (dim >= 45 && dim <= 89) {
                timeAgo =
                    ctx.resources.getString(R.string.date_util_prefix_about) + " " + ctx.resources.getString(
                        R.string.date_util_term_an
                    ) + " " + ctx.resources.getString(
                        R.string.date_util_unit_hour
                    )
            } else if (dim >= 90 && dim <= 1439) {
                timeAgo =
                    ctx.resources.getString(R.string.date_util_prefix_about) + " " + Math.round((dim / 60).toFloat()) + " " + ctx.resources.getString(
                        R.string.date_util_unit_hours
                    )
            } else if (dim >= 1440 && dim <= 2519) {
                timeAgo = "1 " + ctx.resources.getString(R.string.date_util_unit_day)
            } else if (dim >= 2520 && dim <= 43199) {
                timeAgo =
                    Math.round((dim / 1440).toFloat())
                        .toString() + " " + ctx.resources.getString(R.string.date_util_unit_days)
            } else if (dim >= 43200 && dim <= 86399) {
                timeAgo =
                    ctx.resources.getString(R.string.date_util_prefix_about) + " " + ctx.resources.getString(
                        R.string.date_util_term_a
                    ) + " " + ctx.resources.getString(
                        R.string.date_util_unit_month
                    )
            } else if (dim >= 86400 && dim <= 525599) {
                timeAgo =
                    Math.round((dim / 43200).toFloat())
                        .toString() + " " + ctx.resources.getString(R.string.date_util_unit_months)
            } else if (dim >= 525600 && dim <= 655199) {
                timeAgo =
                    ctx.resources.getString(R.string.date_util_prefix_about) + " " + ctx.resources.getString(
                        R.string.date_util_term_a
                    ) + " " + ctx.resources.getString(
                        R.string.date_util_unit_year
                    )
            } else if (dim >= 655200 && dim <= 914399) {
                timeAgo =
                    ctx.resources.getString(R.string.date_util_prefix_over) + " " + ctx.resources.getString(
                        R.string.date_util_term_a
                    ) + " " + ctx.resources.getString(
                        R.string.date_util_unit_year
                    )
            } else if (dim >= 914400 && dim <= 1051199) {
                timeAgo =
                    ctx.resources.getString(R.string.date_util_prefix_almost) + " 2 " + ctx.resources.getString(
                        R.string.date_util_unit_years
                    )
            } else {
                timeAgo =
                    ctx.resources.getString(R.string.date_util_prefix_about) + " " + Math.round((dim / 525600).toFloat()) + " " + ctx.resources.getString(
                        R.string.date_util_unit_years
                    )
            }
            return if (timeAgo == "Just Now") {
                timeAgo
            } else {
                timeAgo + " " + ctx.resources.getString(R.string.date_util_suffix)
            }
        }

    }
}