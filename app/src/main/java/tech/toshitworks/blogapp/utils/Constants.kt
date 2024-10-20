package tech.toshitworks.blogapp.utils

object Constants{
    const val BASEURL: String = "https://blog-app-backend-production-841d.up.railway.app"
    const val SIGNUP = "user"
    const val LOGIN = "auth/login"
    const val VERIFY = "auth/verify"
    const val CATEGORY_ALL = "category"
    const val CATEGORY_ADD = "category"
    const val CATEGORY_BY_ID = "category/{id}"
    const val CATEGORY_BY_TITLE = "category/title/{title}"
    const val POST_BY_CATEGORY = "post/category/{id}"
    const val POST_BY_QUERY = "post/search/{keyword}"
    const val POST_BY_ID = "post/{id}"
    const val POST_BY_USER = "post/user/{id}"
    const val POST_ALL = "post"
    const val COMMENTS_BY_POST = "comment/post/{id}"
    const val ADD_POST = "post/{id}"
    const val ADD_COMMENT = "comment/{id}"
    const val USER_BY_ID = "user/id/{id}"
}
