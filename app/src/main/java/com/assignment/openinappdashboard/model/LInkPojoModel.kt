package com.assignment.openinappdashboard.model

data class LinkPojoModel(
    val status: Boolean,
    val status_code: Long,
    val message: String,
    val support_whatsapp_number: String,
    val extra_income: Double,
    val total_links: Long,
    val total_clicks: Long,
    val today_clicks: Long,
    val top_source: String,
    val top_location: String,
    val startTime: String,
    val links_created_today: Long,
    val applied_campaign: Long,
    val data: LinkVariusTypeData
)

data class LinkVariusTypeData(
    val recent_links: List<LinkInfoData>,
    val top_links: List<LinkInfoData>,
    val overall_url_chart: Map<String, Int>
)

data class LinkInfoData(
    val url_id: Long,
    val web_link: String,
    val smart_link: String,
    val title: String,
    val total_clicks: Long,
    val original_image: String,
    val thumbnail: String,
    val times_ago: String,
    val created_at: String,
    val domain_id: String,
    val url_prefix: String,
    val url_suffix: String,
    val app: String
)

