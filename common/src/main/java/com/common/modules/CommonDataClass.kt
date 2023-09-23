package com.common.modules

enum class GENDER {
    MALE, FEMALE
}
enum class STATUS{
    ACTIVE, DELETED
}

enum class PRIZE(var COINS: Long){
    AD(50),
    CORRECT_ANSWER(5),
    NOT_CORRECT_ANSWER(-5),
    FEE_SPIN_WHEEL(-5),
    SPIN_WHEEL(0),
    FEE_SCRATCH(-5),
    SCRATCH(0)
}
