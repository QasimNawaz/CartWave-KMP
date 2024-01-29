package data.utils

object URLConstants {
    const val BASE_URL = "http://192.168.1.6:8081/"
    const val LOGIN = "${BASE_URL}auth/login"
    const val REGISTER = "${BASE_URL}auth/register"
    const val PRODUCTS_BY_CATEGORY = "${BASE_URL}product/getProductsByCategory"
    const val PRODUCTS_GROUP_BY_CATEGORY = "${BASE_URL}product/getProductsGroupByCategory"
    const val ADD_PRODUCT = "${BASE_URL}product/addProduct"
    const val GET_PRODUCT = "${BASE_URL}product/getProduct"
    const val GET_FAVOURITES = "${BASE_URL}favourite/getFavourites"
    const val ADD_TO_FAVOURITE = "${BASE_URL}favourite/addToFavourite"
    const val REMOVE_FROM_FAVOURITE = "${BASE_URL}favourite/removeFromFavourite"
    const val GET_USER_CART = "${BASE_URL}cart/getUserCart"
    const val ADD_TO_CART = "${BASE_URL}cart/addToCart"
    const val REMOVE_FROM_CART = "${BASE_URL}cart/removeFromCart"
    const val ADD_ADDRESS = "${BASE_URL}address/addAddress"
    const val UPDATE_PRIMARY_ADDRESS = "${BASE_URL}address/updatePrimaryAddress"
    const val GET_PRIMARY_ADDRESS = "${BASE_URL}address/getPrimaryAddress"
    const val GET_ADDRESSES = "${BASE_URL}address/getAddress"
    const val PLACE_ORDER = "${BASE_URL}order/placeOrder"
}