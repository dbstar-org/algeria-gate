package io.github.dbstarll.algeria.boot.controller.mock;

import io.github.dbstarll.algeria.boot.model.api.request.QueryCatalogToneRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/rbt")
class MockRbtController {
    private static final String RES_QUERY_CATALOG_TONE_SUCCESS = "{\n" +
            "\t\"returnCode\": \"000000\",\n" +
            "\t\"resultInfo\": null,\n" +
            "\t\"transactionID\": null,\n" +
            "\t\"operationID\": 0,\n" +
            "\t\"eventClassName\": null,\n" +
            "\t\"resultCode\": 0,\n" +
            "\t\"recordSum\": \"-1\",\n" +
            "\t\"toneInfos\": [{\n" +
            "\t\t\t\"userToneCode\": null,\n" +
            "\t\t\t\"askType\": null,\n" +
            "\t\t\t\"modifyID\": null,\n" +
            "\t\t\t\"personID\": null,\n" +
            "\t\t\t\"toneID\": \"13239642\",\n" +
            "\t\t\t\"toneCode\": \"588004\",\n" +
            "\t\t\t\"toneCodeLong\": \"601588000000000004\",\n" +
            "\t\t\t\"toneName\": \"ddd\",\n" +
            "\t\t\t\"toneNameLetter\": \"D\",\n" +
            "\t\t\t\"singerName\": \"ddd\",\n" +
            "\t\t\t\"singerSex\": \"1\",\n" +
            "\t\t\t\"singerNameLetter\": \"D\",\n" +
            "\t\t\t\"language\": \"3\",\n" +
            "\t\t\t\"category\": null,\n" +
            "\t\t\t\"categoryName\": null,\n" +
            "\t\t\t\"cpCode\": \"601588\",\n" +
            "\t\t\t\"cpName\": \"tztest\",\n" +
            "\t\t\t\"price\": \"5000\",\n" +
            "\t\t\t\"updateTime\": \"2024-06-12 09:21:57\",\n" +
            "\t\t\t\"orderTimes\": \"0\",\n" +
            "\t\t\t\"setTimes\": \"0\",\n" +
            "\t\t\t\"checkTime\": \"2024-06-12 09:21:57\",\n" +
            "\t\t\t\"toneValidDay\": \"2024-08-30 23:59:59\",\n" +
            "\t\t\t\"uploadTime\": \"2024-06-12 09:21:57\",\n" +
            "\t\t\t\"initialDownTime\": null,\n" +
            "\t\t\t\"info\": null,\n" +
            "\t\t\t\"toneAddress\": \"https://7.220.237.147:8135/colorring/rl/601/588/0/0000/0000/004.wav\",\n" +
            "\t\t\t\"hdToneAddress\": null,\n" +
            "\t\t\t\"tonePreListenAddress\": \"https://7.220.237.147:8135/colorring/al/601/588/0/0000/0000/004.wav\",\n" +
            "\t\t\t\"toneNameAddress\": null,\n" +
            "\t\t\t\"singerNameAddress\": null,\n" +
            "\t\t\t\"deviceAndUrl\": \"\",\n" +
            "\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\"totalPrice\": null,\n" +
            "\t\t\t\"tableType\": \"2\",\n" +
            "\t\t\t\"tonePath\": \"Y:/audio/rl/601/588/0/0000/0000/004.wav\",\n" +
            "\t\t\t\"hdTonePath\": null,\n" +
            "\t\t\t\"tonePreListenPath\": \"Y:/audio/al/601/588/0/0000/0000/004.wav\",\n" +
            "\t\t\t\"singerNamePath\": null,\n" +
            "\t\t\t\"toneNamePath\": null,\n" +
            "\t\t\t\"corpCode\": null,\n" +
            "\t\t\t\"corpName\": null,\n" +
            "\t\t\t\"diyUserPhoneNumber\": null,\n" +
            "\t\t\t\"plusUserPhoneNumber\": null,\n" +
            "\t\t\t\"uploadType\": \"1\",\n" +
            "\t\t\t\"orderInfo\": \"1\",\n" +
            "\t\t\t\"offset\": null,\n" +
            "\t\t\t\"rejectReason\": null,\n" +
            "\t\t\t\"index\": \"4\",\n" +
            "\t\t\t\"endOffset\": null,\n" +
            "\t\t\t\"relativeTime\": \"60\",\n" +
            "\t\t\t\"taxisIndex\": null,\n" +
            "\t\t\t\"taxisToneDownInfo\": null,\n" +
            "\t\t\t\"availableDateTime\": null,\n" +
            "\t\t\t\"remark\": null,\n" +
            "\t\t\t\"cutFlag\": \"0\",\n" +
            "\t\t\t\"musicID\": null,\n" +
            "\t\t\t\"enabledDate\": null,\n" +
            "\t\t\t\"downTime\": null,\n" +
            "\t\t\t\"tariffPrice\": null,\n" +
            "\t\t\t\"priceGroupID\": \"-1\",\n" +
            "\t\t\t\"reOrderMode\": \"\",\n" +
            "\t\t\t\"lyricCompanyID\": null,\n" +
            "\t\t\t\"lyricValidDay\": null,\n" +
            "\t\t\t\"tuneCompanyID\": null,\n" +
            "\t\t\t\"tuneValidDay\": null,\n" +
            "\t\t\t\"recordCompanyID\": null,\n" +
            "\t\t\t\"recordValidDay\": null,\n" +
            "\t\t\t\"reason\": \"\",\n" +
            "\t\t\t\"webTonePreListenAddress\": null,\n" +
            "\t\t\t\"aipTonePreListenAddress\": null,\n" +
            "\t\t\t\"aipTonePreListenURL\": null,\n" +
            "\t\t\t\"contentLanguage\": \"3\",\n" +
            "\t\t\t\"productID\": null,\n" +
            "\t\t\t\"passID\": null,\n" +
            "\t\t\t\"merchantID\": null,\n" +
            "\t\t\t\"operator\": null,\n" +
            "\t\t\t\"resourceServiceType\": \"1\",\n" +
            "\t\t\t\"fileSyncPath\": null,\n" +
            "\t\t\t\"fileSyncInterval\": null,\n" +
            "\t\t\t\"beginTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"ftpUserName\": null,\n" +
            "\t\t\t\"ftpPassword\": null,\n" +
            "\t\t\t\"allowedChannel\": null,\n" +
            "\t\t\t\"freeOrderTimes\": null,\n" +
            "\t\t\t\"serviceID\": null,\n" +
            "\t\t\t\"cpRevSharePercentage\": null,\n" +
            "\t\t\t\"aggregatorShare\": null,\n" +
            "\t\t\t\"cpType\": null,\n" +
            "\t\t\t\"streamingURL\": null,\n" +
            "\t\t\t\"rate\": null,\n" +
            "\t\t\t\"productOrderKey\": null,\n" +
            "\t\t\t\"chargeProductID\": null,\n" +
            "\t\t\t\"inboxStatus\": null,\n" +
            "\t\t\t\"phoneNumber\": null,\n" +
            "\t\t\t\"settingToneStatus\": null,\n" +
            "\t\t\t\"unlimitFlag\": null,\n" +
            "\t\t\t\"groupID\": null,\n" +
            "\t\t\t\"resourceType\": null,\n" +
            "\t\t\t\"dailyChargeFlag\": null,\n" +
            "\t\t\t\"maxDownloadLimit\": null,\n" +
            "\t\t\t\"renewFlag\": null,\n" +
            "\t\t\t\"singerInfos\": null,\n" +
            "\t\t\t\"webImagePath\": null,\n" +
            "\t\t\t\"wapImagePath\": null,\n" +
            "\t\t\t\"userConsent\": null,\n" +
            "\t\t\t\"keyword\": null,\n" +
            "\t\t\t\"smAccessCode\": null,\n" +
            "\t\t\t\"adminAct\": null,\n" +
            "\t\t\t\"extendAbsoluteDateFlag\": null,\n" +
            "\t\t\t\"isrc\": null,\n" +
            "\t\t\t\"grid\": null,\n" +
            "\t\t\t\"lastChargedPrice\": null,\n" +
            "\t\t\t\"lastChargedDuration\": null,\n" +
            "\t\t\t\"shortCode\": null,\n" +
            "\t\t\t\"creditDownload\": null,\n" +
            "\t\t\t\"retryCount\": null,\n" +
            "\t\t\t\"lastRenewTryTime\": null,\n" +
            "\t\t\t\"replaceCount\": null,\n" +
            "\t\t\t\"changeTime\": null,\n" +
            "\t\t\t\"requestDiscount\": null,\n" +
            "\t\t\t\"renewFailureCount\": null,\n" +
            "\t\t\t\"isMicroCharge\": null,\n" +
            "\t\t\t\"isDebtCharge\": null,\n" +
            "\t\t\t\"monthFeeEndDay\": null,\n" +
            "\t\t\t\"nextMonthStartDate\": null,\n" +
            "\t\t\t\"renewContinueCount\": null,\n" +
            "\t\t\t\"accountType\": null,\n" +
            "\t\t\t\"contractPrice\": null,\n" +
            "\t\t\t\"multiAccountValue\": null,\n" +
            "\t\t\t\"requestDuration\": null,\n" +
            "\t\t\t\"requestPrice\": null,\n" +
            "\t\t\t\"gracePeriod\": null,\n" +
            "\t\t\t\"likeTimes\": null,\n" +
            "\t\t\t\"mateInfo\": null\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"userToneCode\": null,\n" +
            "\t\t\t\"askType\": null,\n" +
            "\t\t\t\"modifyID\": null,\n" +
            "\t\t\t\"personID\": null,\n" +
            "\t\t\t\"toneID\": \"13239639\",\n" +
            "\t\t\t\"toneCode\": \"588001\",\n" +
            "\t\t\t\"toneCodeLong\": \"601588000000000001\",\n" +
            "\t\t\t\"toneName\": \"aaa\",\n" +
            "\t\t\t\"toneNameLetter\": \"A\",\n" +
            "\t\t\t\"singerName\": \"aaa\",\n" +
            "\t\t\t\"singerSex\": \"1\",\n" +
            "\t\t\t\"singerNameLetter\": \"A\",\n" +
            "\t\t\t\"language\": \"3\",\n" +
            "\t\t\t\"category\": null,\n" +
            "\t\t\t\"categoryName\": null,\n" +
            "\t\t\t\"cpCode\": \"601588\",\n" +
            "\t\t\t\"cpName\": \"tztest\",\n" +
            "\t\t\t\"price\": \"5000\",\n" +
            "\t\t\t\"updateTime\": \"2024-06-12 09:20:22\",\n" +
            "\t\t\t\"orderTimes\": \"0\",\n" +
            "\t\t\t\"setTimes\": \"0\",\n" +
            "\t\t\t\"checkTime\": \"2024-06-12 09:20:22\",\n" +
            "\t\t\t\"toneValidDay\": \"2024-07-31 23:59:59\",\n" +
            "\t\t\t\"uploadTime\": \"2024-06-12 09:20:22\",\n" +
            "\t\t\t\"initialDownTime\": null,\n" +
            "\t\t\t\"info\": null,\n" +
            "\t\t\t\"toneAddress\": \"https://7.220.237.147:8135/colorring/rl/601/588/0/0000/0000/001.wav\",\n" +
            "\t\t\t\"hdToneAddress\": null,\n" +
            "\t\t\t\"tonePreListenAddress\": \"https://7.220.237.147:8135/colorring/al/601/588/0/0000/0000/001.wav\",\n" +
            "\t\t\t\"toneNameAddress\": null,\n" +
            "\t\t\t\"singerNameAddress\": null,\n" +
            "\t\t\t\"deviceAndUrl\": \"\",\n" +
            "\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\"totalPrice\": null,\n" +
            "\t\t\t\"tableType\": \"2\",\n" +
            "\t\t\t\"tonePath\": \"Y:/audio/rl/601/588/0/0000/0000/001.wav\",\n" +
            "\t\t\t\"hdTonePath\": null,\n" +
            "\t\t\t\"tonePreListenPath\": \"Y:/audio/al/601/588/0/0000/0000/001.wav\",\n" +
            "\t\t\t\"singerNamePath\": null,\n" +
            "\t\t\t\"toneNamePath\": null,\n" +
            "\t\t\t\"corpCode\": null,\n" +
            "\t\t\t\"corpName\": null,\n" +
            "\t\t\t\"diyUserPhoneNumber\": null,\n" +
            "\t\t\t\"plusUserPhoneNumber\": null,\n" +
            "\t\t\t\"uploadType\": \"1\",\n" +
            "\t\t\t\"orderInfo\": \"2\",\n" +
            "\t\t\t\"offset\": null,\n" +
            "\t\t\t\"rejectReason\": null,\n" +
            "\t\t\t\"index\": \"1\",\n" +
            "\t\t\t\"endOffset\": null,\n" +
            "\t\t\t\"relativeTime\": \"30\",\n" +
            "\t\t\t\"taxisIndex\": null,\n" +
            "\t\t\t\"taxisToneDownInfo\": null,\n" +
            "\t\t\t\"availableDateTime\": null,\n" +
            "\t\t\t\"remark\": null,\n" +
            "\t\t\t\"cutFlag\": \"0\",\n" +
            "\t\t\t\"musicID\": null,\n" +
            "\t\t\t\"enabledDate\": null,\n" +
            "\t\t\t\"downTime\": null,\n" +
            "\t\t\t\"tariffPrice\": null,\n" +
            "\t\t\t\"priceGroupID\": \"-1\",\n" +
            "\t\t\t\"reOrderMode\": \"\",\n" +
            "\t\t\t\"lyricCompanyID\": null,\n" +
            "\t\t\t\"lyricValidDay\": null,\n" +
            "\t\t\t\"tuneCompanyID\": null,\n" +
            "\t\t\t\"tuneValidDay\": null,\n" +
            "\t\t\t\"recordCompanyID\": null,\n" +
            "\t\t\t\"recordValidDay\": null,\n" +
            "\t\t\t\"reason\": \"\",\n" +
            "\t\t\t\"webTonePreListenAddress\": null,\n" +
            "\t\t\t\"aipTonePreListenAddress\": null,\n" +
            "\t\t\t\"aipTonePreListenURL\": null,\n" +
            "\t\t\t\"contentLanguage\": \"3\",\n" +
            "\t\t\t\"productID\": null,\n" +
            "\t\t\t\"passID\": null,\n" +
            "\t\t\t\"merchantID\": null,\n" +
            "\t\t\t\"operator\": null,\n" +
            "\t\t\t\"resourceServiceType\": \"1\",\n" +
            "\t\t\t\"fileSyncPath\": null,\n" +
            "\t\t\t\"fileSyncInterval\": null,\n" +
            "\t\t\t\"beginTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"ftpUserName\": null,\n" +
            "\t\t\t\"ftpPassword\": null,\n" +
            "\t\t\t\"allowedChannel\": null,\n" +
            "\t\t\t\"freeOrderTimes\": null,\n" +
            "\t\t\t\"serviceID\": null,\n" +
            "\t\t\t\"cpRevSharePercentage\": null,\n" +
            "\t\t\t\"aggregatorShare\": null,\n" +
            "\t\t\t\"cpType\": null,\n" +
            "\t\t\t\"streamingURL\": null,\n" +
            "\t\t\t\"rate\": null,\n" +
            "\t\t\t\"productOrderKey\": null,\n" +
            "\t\t\t\"chargeProductID\": null,\n" +
            "\t\t\t\"inboxStatus\": null,\n" +
            "\t\t\t\"phoneNumber\": null,\n" +
            "\t\t\t\"settingToneStatus\": null,\n" +
            "\t\t\t\"unlimitFlag\": null,\n" +
            "\t\t\t\"groupID\": null,\n" +
            "\t\t\t\"resourceType\": null,\n" +
            "\t\t\t\"dailyChargeFlag\": null,\n" +
            "\t\t\t\"maxDownloadLimit\": null,\n" +
            "\t\t\t\"renewFlag\": null,\n" +
            "\t\t\t\"singerInfos\": null,\n" +
            "\t\t\t\"webImagePath\": null,\n" +
            "\t\t\t\"wapImagePath\": null,\n" +
            "\t\t\t\"userConsent\": null,\n" +
            "\t\t\t\"keyword\": null,\n" +
            "\t\t\t\"smAccessCode\": null,\n" +
            "\t\t\t\"adminAct\": null,\n" +
            "\t\t\t\"extendAbsoluteDateFlag\": null,\n" +
            "\t\t\t\"isrc\": null,\n" +
            "\t\t\t\"grid\": null,\n" +
            "\t\t\t\"lastChargedPrice\": null,\n" +
            "\t\t\t\"lastChargedDuration\": null,\n" +
            "\t\t\t\"shortCode\": null,\n" +
            "\t\t\t\"creditDownload\": null,\n" +
            "\t\t\t\"retryCount\": null,\n" +
            "\t\t\t\"lastRenewTryTime\": null,\n" +
            "\t\t\t\"replaceCount\": null,\n" +
            "\t\t\t\"changeTime\": null,\n" +
            "\t\t\t\"requestDiscount\": null,\n" +
            "\t\t\t\"renewFailureCount\": null,\n" +
            "\t\t\t\"isMicroCharge\": null,\n" +
            "\t\t\t\"isDebtCharge\": null,\n" +
            "\t\t\t\"monthFeeEndDay\": null,\n" +
            "\t\t\t\"nextMonthStartDate\": null,\n" +
            "\t\t\t\"renewContinueCount\": null,\n" +
            "\t\t\t\"accountType\": null,\n" +
            "\t\t\t\"contractPrice\": null,\n" +
            "\t\t\t\"multiAccountValue\": null,\n" +
            "\t\t\t\"requestDuration\": null,\n" +
            "\t\t\t\"requestPrice\": null,\n" +
            "\t\t\t\"gracePeriod\": null,\n" +
            "\t\t\t\"likeTimes\": null,\n" +
            "\t\t\t\"mateInfo\": null\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"userToneCode\": null,\n" +
            "\t\t\t\"askType\": null,\n" +
            "\t\t\t\"modifyID\": null,\n" +
            "\t\t\t\"personID\": null,\n" +
            "\t\t\t\"toneID\": \"13239641\",\n" +
            "\t\t\t\"toneCode\": \"588003\",\n" +
            "\t\t\t\"toneCodeLong\": \"601588000000000003\",\n" +
            "\t\t\t\"toneName\": \"ccc\",\n" +
            "\t\t\t\"toneNameLetter\": \"C\",\n" +
            "\t\t\t\"singerName\": \"ccc\",\n" +
            "\t\t\t\"singerSex\": \"1\",\n" +
            "\t\t\t\"singerNameLetter\": \"C\",\n" +
            "\t\t\t\"language\": \"3\",\n" +
            "\t\t\t\"category\": null,\n" +
            "\t\t\t\"categoryName\": null,\n" +
            "\t\t\t\"cpCode\": \"601588\",\n" +
            "\t\t\t\"cpName\": \"tztest\",\n" +
            "\t\t\t\"price\": \"5000\",\n" +
            "\t\t\t\"updateTime\": \"2024-06-12 09:21:37\",\n" +
            "\t\t\t\"orderTimes\": \"0\",\n" +
            "\t\t\t\"setTimes\": \"0\",\n" +
            "\t\t\t\"checkTime\": \"2024-06-12 09:21:37\",\n" +
            "\t\t\t\"toneValidDay\": \"2024-08-28 23:59:59\",\n" +
            "\t\t\t\"uploadTime\": \"2024-06-12 09:21:37\",\n" +
            "\t\t\t\"initialDownTime\": null,\n" +
            "\t\t\t\"info\": null,\n" +
            "\t\t\t\"toneAddress\": \"https://7.220.237.147:8135/colorring/rl/601/588/0/0000/0000/003.wav\",\n" +
            "\t\t\t\"hdToneAddress\": null,\n" +
            "\t\t\t\"tonePreListenAddress\": \"https://7.220.237.147:8135/colorring/al/601/588/0/0000/0000/003.wav\",\n" +
            "\t\t\t\"toneNameAddress\": null,\n" +
            "\t\t\t\"singerNameAddress\": null,\n" +
            "\t\t\t\"deviceAndUrl\": \"\",\n" +
            "\t\t\t\"status\": \"1\",\n" +
            "\t\t\t\"totalPrice\": null,\n" +
            "\t\t\t\"tableType\": \"2\",\n" +
            "\t\t\t\"tonePath\": \"Y:/audio/rl/601/588/0/0000/0000/003.wav\",\n" +
            "\t\t\t\"hdTonePath\": null,\n" +
            "\t\t\t\"tonePreListenPath\": \"Y:/audio/al/601/588/0/0000/0000/003.wav\",\n" +
            "\t\t\t\"singerNamePath\": null,\n" +
            "\t\t\t\"toneNamePath\": null,\n" +
            "\t\t\t\"corpCode\": null,\n" +
            "\t\t\t\"corpName\": null,\n" +
            "\t\t\t\"diyUserPhoneNumber\": null,\n" +
            "\t\t\t\"plusUserPhoneNumber\": null,\n" +
            "\t\t\t\"uploadType\": \"1\",\n" +
            "\t\t\t\"orderInfo\": \"3\",\n" +
            "\t\t\t\"offset\": null,\n" +
            "\t\t\t\"rejectReason\": null,\n" +
            "\t\t\t\"index\": \"3\",\n" +
            "\t\t\t\"endOffset\": null,\n" +
            "\t\t\t\"relativeTime\": \"60\",\n" +
            "\t\t\t\"taxisIndex\": null,\n" +
            "\t\t\t\"taxisToneDownInfo\": null,\n" +
            "\t\t\t\"availableDateTime\": null,\n" +
            "\t\t\t\"remark\": null,\n" +
            "\t\t\t\"cutFlag\": \"0\",\n" +
            "\t\t\t\"musicID\": null,\n" +
            "\t\t\t\"enabledDate\": null,\n" +
            "\t\t\t\"downTime\": null,\n" +
            "\t\t\t\"tariffPrice\": null,\n" +
            "\t\t\t\"priceGroupID\": \"-1\",\n" +
            "\t\t\t\"reOrderMode\": \"\",\n" +
            "\t\t\t\"lyricCompanyID\": null,\n" +
            "\t\t\t\"lyricValidDay\": null,\n" +
            "\t\t\t\"tuneCompanyID\": null,\n" +
            "\t\t\t\"tuneValidDay\": null,\n" +
            "\t\t\t\"recordCompanyID\": null,\n" +
            "\t\t\t\"recordValidDay\": null,\n" +
            "\t\t\t\"reason\": \"\",\n" +
            "\t\t\t\"webTonePreListenAddress\": null,\n" +
            "\t\t\t\"aipTonePreListenAddress\": null,\n" +
            "\t\t\t\"aipTonePreListenURL\": null,\n" +
            "\t\t\t\"contentLanguage\": \"3\",\n" +
            "\t\t\t\"productID\": null,\n" +
            "\t\t\t\"passID\": null,\n" +
            "\t\t\t\"merchantID\": null,\n" +
            "\t\t\t\"operator\": null,\n" +
            "\t\t\t\"resourceServiceType\": \"1\",\n" +
            "\t\t\t\"fileSyncPath\": null,\n" +
            "\t\t\t\"fileSyncInterval\": null,\n" +
            "\t\t\t\"beginTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"ftpUserName\": null,\n" +
            "\t\t\t\"ftpPassword\": null,\n" +
            "\t\t\t\"allowedChannel\": null,\n" +
            "\t\t\t\"freeOrderTimes\": null,\n" +
            "\t\t\t\"serviceID\": null,\n" +
            "\t\t\t\"cpRevSharePercentage\": null,\n" +
            "\t\t\t\"aggregatorShare\": null,\n" +
            "\t\t\t\"cpType\": null,\n" +
            "\t\t\t\"streamingURL\": null,\n" +
            "\t\t\t\"rate\": null,\n" +
            "\t\t\t\"productOrderKey\": null,\n" +
            "\t\t\t\"chargeProductID\": null,\n" +
            "\t\t\t\"inboxStatus\": null,\n" +
            "\t\t\t\"phoneNumber\": null,\n" +
            "\t\t\t\"settingToneStatus\": null,\n" +
            "\t\t\t\"unlimitFlag\": null,\n" +
            "\t\t\t\"groupID\": null,\n" +
            "\t\t\t\"resourceType\": null,\n" +
            "\t\t\t\"dailyChargeFlag\": null,\n" +
            "\t\t\t\"maxDownloadLimit\": null,\n" +
            "\t\t\t\"renewFlag\": null,\n" +
            "\t\t\t\"singerInfos\": null,\n" +
            "\t\t\t\"webImagePath\": null,\n" +
            "\t\t\t\"wapImagePath\": null,\n" +
            "\t\t\t\"userConsent\": null,\n" +
            "\t\t\t\"keyword\": null,\n" +
            "\t\t\t\"smAccessCode\": null,\n" +
            "\t\t\t\"adminAct\": null,\n" +
            "\t\t\t\"extendAbsoluteDateFlag\": null,\n" +
            "\t\t\t\"isrc\": null,\n" +
            "\t\t\t\"grid\": null,\n" +
            "\t\t\t\"lastChargedPrice\": null,\n" +
            "\t\t\t\"lastChargedDuration\": null,\n" +
            "\t\t\t\"shortCode\": null,\n" +
            "\t\t\t\"creditDownload\": null,\n" +
            "\t\t\t\"retryCount\": null,\n" +
            "\t\t\t\"lastRenewTryTime\": null,\n" +
            "\t\t\t\"replaceCount\": null,\n" +
            "\t\t\t\"changeTime\": null,\n" +
            "\t\t\t\"requestDiscount\": null,\n" +
            "\t\t\t\"renewFailureCount\": null,\n" +
            "\t\t\t\"isMicroCharge\": null,\n" +
            "\t\t\t\"isDebtCharge\": null,\n" +
            "\t\t\t\"monthFeeEndDay\": null,\n" +
            "\t\t\t\"nextMonthStartDate\": null,\n" +
            "\t\t\t\"renewContinueCount\": null,\n" +
            "\t\t\t\"accountType\": null,\n" +
            "\t\t\t\"contractPrice\": null,\n" +
            "\t\t\t\"multiAccountValue\": null,\n" +
            "\t\t\t\"requestDuration\": null,\n" +
            "\t\t\t\"requestPrice\": null,\n" +
            "\t\t\t\"gracePeriod\": null,\n" +
            "\t\t\t\"likeTimes\": null,\n" +
            "\t\t\t\"mateInfo\": null\n" +
            "\t\t}\n" +
            "\t],\n" +
            "\t\"toneBoxInfos\": null,\n" +
            "\t\"pictureInfos\": null,\n" +
            "\t\"fullTrackInfos\": null\n" +
            "}";

    private static final String RES_QUERY_CATALOG_TONE_FAILED = "{\n" +
            "    \"returnCode\": \"200002\",\n" +
            "    \"resultInfo\": null,\n" +
            "    \"transactionID\": null,\n" +
            "    \"operationID\": 0,\n" +
            "    \"eventClassName\": null,\n" +
            "    \"resultCode\": 0,\n" +
            "    \"recordSum\": null,\n" +
            "    \"toneInfos\": null,\n" +
            "    \"toneBoxInfos\": null,\n" +
            "    \"pictureInfos\": null,\n" +
            "    \"fullTrackInfos\": null\n" +
            "}\n";

    @PostMapping("/toneprovide/querycatalogtone")
    String queryCatalogTone(@Valid @RequestBody QueryCatalogToneRequest request) {
        if ("5".equals(request.getStatus())) {
            return RES_QUERY_CATALOG_TONE_FAILED;
        } else {
            return RES_QUERY_CATALOG_TONE_SUCCESS;
        }
    }
}
