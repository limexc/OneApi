/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.common.constant;

import java.util.regex.Pattern;

/**
 * 常用正则
 *
 * @author LIMEXC
 */

public final class ValidationConstants {

    /**
     * Email正则表达式
     */
    public static final Pattern EMAIL = Pattern.compile("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");

    /**
     * 电话号码正则表达式
     */
    public static final Pattern PHONE = Pattern.compile("(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)");

    /**
     * 手机号码正则表达式
     */
    public static final Pattern MOBILE = Pattern.compile("^(13[0-9]|15[0-9]|18[0-9]|17[0-9])\\d{8}$");

    /**
     * Integer正则表达式
     */
    public static final Pattern INTEGER = Pattern.compile("^-?(([1-9]\\d*$)|0)");

    /**
     * 正整数正则表达式 >=0
     */
    public static final Pattern INTEGER_NEGATIVE = Pattern.compile("^[1-9]\\d*|0$");

    /**
     * 负整数正则表达式 <=0
     */
    public static final Pattern INTEGER_POSITIVE = Pattern.compile("^-[1-9]\\d*|0$");

    /**
     * Double正则表达式
     */
    public static final Pattern DOUBLE = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");

    /**
     * 正Double正则表达式 >=0
     */
    public static final Pattern DOUBLE_NEGATIVE = Pattern.compile("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$");

    /**
     * 负Double正则表达式 <= 0
     */
    public static final Pattern DOUBLE_POSITIVE = Pattern.compile("^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$");

    /**
     * 年龄正则表达式
     */
    public static final Pattern AGE = Pattern.compile("^(?:[1-9][0-9]?|1[01][0-9]|120)$");

    /**
     * 邮编正则表达式 国内6位邮编
     */
    public static final Pattern CODE = Pattern.compile("[0-9]\\d{5}(?!\\d)");

    /**
     * 匹配由数字、26个英文字母或者下划线组成的字符串
     */
    public static final Pattern STR_ENG_NUM_ = Pattern.compile("^\\w+$");

    /**
     * 匹配由数字和26个英文字母组成的字符串
     */
    public static final Pattern STR_ENG_NUM = Pattern.compile("^[A-Za-z0-9]+");

    /**
     * 匹配由26个英文字母组成的字符串
     */
    public static final Pattern STR_ENG = Pattern.compile("^[A-Za-z]+$");

    /**
     * 回车、制表、换行、空格字符
     */
    public static final Pattern STR_NULL_SPECIAL = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * 过滤特殊字符串正则 下划线_除外
     */
    public static final Pattern STR_SPECIAL = Pattern.compile("[-`~!@#$%^&*()+=|{}':;'\",\\[\\].<>/?·~！@#￥%……&*（）——+|{}《》【】‘；：”“’。，、？]");

    /**
     * 日期正则 支持：
     * YYYY-MM-DD
     * YYYY/MM/DD
     * YYYY_MM_DD
     * YYYYMMDD
     * YYYY.MM.DD的形式
     */
    public static final Pattern DATE_ALL = Pattern.compile("((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)" +
            "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)" +
            "(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)" +
            "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|" +
            "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))");

    /***
     * 日期正则 支持：
     * YYYY-MM-DD
     */
    public static final Pattern DATE_FORMAT1 = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");

    /**
     * URL正则表达式
     * 匹配 http www ftp
     */
    public static final Pattern URL = Pattern.compile("^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?" +
            "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*" +
            "(\\w*:)*(\\w*\\+)*(\\w*\\.)*" +
            "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$");

    /**
     * 身份证正则表达式
     */
    public static final Pattern ID_CARD = Pattern.compile("((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +
            "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" +
            "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))");

    /**
     * 匹配数字组成的字符串
     */
    public static final Pattern STR_NUM = Pattern.compile("^[0-9]+$");
}
