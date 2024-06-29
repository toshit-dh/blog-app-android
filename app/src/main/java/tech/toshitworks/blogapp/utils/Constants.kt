package tech.toshitworks.blogapp.utils

object Constants{
    const val BASEURL: String = "http://192.168.1.12:8080"
    const val SIGNUP = "user"
    const val LOGIN = "auth/login"
    const val VERIFY = "auth"
    const val CATEGORY_ALL = "category"
    const val CATEGORY_BY_ID = "category/{id}"
    const val CATEGORY_BY_TITLE = "category/title/{title}"
    const val POST_BY_CATEGORY = "post/category/{id}"
    const val POST_BY_QUERY = "post/search/{keyword}"
    const val POST_BY_ID = "post/{id}"
    const val POST_ALL = "post"
    const val COMMENTS_BY_POST = "comment/post/{id}"
}
