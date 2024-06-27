package io.github.dbstarll.algeria.boot.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.algeria.boot.api.RbtApi.RbtApiException;
import io.github.dbstarll.utils.net.api.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class RbtApiTest {
    private static final String TEST_MOBILE = "18210008434";
    private static final String TEST_TONE_ID = "13229395";

    @Autowired
    private RbtApi rbtApi;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void queryCatalogTone() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.tone().query("1"));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("toneInfos").size());
        assertEquals(35, body.at("/toneInfos/0").size());
        assertEquals(TEST_TONE_ID, body.at("/toneInfos/0/toneID").textValue());
        assertTrue(body.at("/toneInfos/0/singerInfos").isMissingNode());
    }

    @Test
    void queryCatalogToneFailed() {
        final RbtApiException e = assertThrowsExactly(RbtApiException.class, () -> rbtApi.tone().query("5"));
        assertEquals("RBT call failed[200002]", e.getMessage());
        assertEquals("200002", e.getReturnCode());
        assertNull(e.getCause());
    }

    @Test
    void getFile() throws IOException, ApiException {
        final byte[] data = rbtApi.tone().get(TEST_TONE_ID);
        assertNotNull(data);
        assertEquals(7737, data.length);
    }

    @Test
    void getFileNotExist() {
        final RbtApiException e = assertThrowsExactly(RbtApiException.class, () -> rbtApi.tone().get("13229000"));
        assertEquals("RBT call failed[302003]", e.getMessage());
        assertEquals("302003", e.getReturnCode());
        assertNull(e.getCause());
    }

    @Test
    void queryUser() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().queryUser(TEST_MOBILE));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("userInfos").size());
        assertEquals(29, body.at("/userInfos/0").size());
        assertEquals(TEST_MOBILE, body.at("/userInfos/0/phoneNumber").textValue());
        assertEquals(1, body.at("/userInfos/0/serviceOrders").size());
        assertEquals(18, body.at("/userInfos/0/serviceOrders/0").size());
        assertEquals("44070863", body.at("/userInfos/0/serviceOrders/0/orderID").textValue());
    }

    @Test
    void queryInboxTone() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().tone().queryInboxTone(TEST_MOBILE));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("toneInfos").size());
        assertEquals(52, body.at("/toneInfos/0").size());
        assertEquals("13219578", body.at("/toneInfos/0/toneID").textValue());
        assertTrue(body.at("/toneInfos/0/singerInfos").isMissingNode());
    }

    @Test
    void delInboxTone() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().tone().delInboxTone(TEST_MOBILE, TEST_TONE_ID));
        assertNotNull(body);
        assertEquals(4, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals("1#13229395", body.get("resultInfo").textValue());
    }

    @Test
    void queryUserProduct() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().queryUserProduct(TEST_MOBILE));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("userProductInfos").size());
        assertEquals(2, body.at("/userProductInfos/0").size());
        assertEquals(TEST_MOBILE, body.at("/userProductInfos/0/phoneNumber").textValue());
        assertEquals(1, body.at("/userProductInfos/0/productinfos").size());
        assertEquals(11, body.at("/userProductInfos/0/productinfos/0").size());
        assertEquals("13", body.at("/userProductInfos/0/productinfos/0/productID").textValue());
        assertEquals("2", body.at("/userProductInfos/0/productinfos/0/status").textValue());
        assertTrue(body.at("/userProductInfos/0/productinfos/0/createTime").isTextual());
        assertEquals("2024-06-26 10:30:07", body.at("/userProductInfos/0/productinfos/0/initialCreateTime").textValue());
        assertEquals("1", body.at("/userProductInfos/0/productinfos/0/renewMode").textValue());
        assertTrue(body.at("/userProductInfos/0/productinfos/0/monthFeeEndDate").isTextual());
        assertEquals("0", body.at("/userProductInfos/0/productinfos/0/isTrialUser").textValue());
        assertEquals("0", body.at("/userProductInfos/0/productinfos/0/isArrear").textValue());
        assertEquals("71", body.at("/userProductInfos/0/productinfos/0/portalType").textValue());
        assertTrue(body.at("/userProductInfos/0/productinfos/0/chargeBeginTime").isTextual());
        assertEquals("100", body.at("/userProductInfos/0/productinfos/0/chargedPrice").textValue());
    }

    @Test
    void subscribe() {
        final RbtApiException e = assertThrowsExactly(RbtApiException.class, () -> rbtApi.user().subscribe(TEST_MOBILE));
        assertEquals("RBT call failed[301009]", e.getMessage());
        assertEquals("301009", e.getReturnCode());
        assertNull(e.getCause());
    }

    @Test
    void subscribeProduct() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().subscribeProduct(TEST_MOBILE));
        assertNotNull(body);
        assertEquals(4, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("returnObjects").size());
        assertEquals(4, body.at("/returnObjects/0").size());
        assertEquals(TEST_MOBILE, body.at("/returnObjects/0/phoneNumber").textValue());
        assertEquals("000000", body.at("/returnObjects/0/resultCode").textValue());
        assertEquals("2", body.at("/returnObjects/0/status").textValue());
        assertEquals("", body.at("/returnObjects/0/serviceID").textValue());
    }

    @Test
    void unsubscribeProduct() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().unsubscribeProduct(TEST_MOBILE));
        assertNotNull(body);
        assertEquals(4, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("returnObjects").size());
        assertEquals(4, body.at("/returnObjects/0").size());
        assertEquals(TEST_MOBILE, body.at("/returnObjects/0/phoneNumber").textValue());
        assertEquals("000000", body.at("/returnObjects/0/resultCode").textValue());
        assertEquals("4", body.at("/returnObjects/0/status").textValue());
        assertEquals("", body.at("/returnObjects/0/serviceID").textValue());
    }

    @Test
    void easyDownload() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().easyDownload(TEST_MOBILE, TEST_TONE_ID));
        assertNotNull(body);
        assertEquals(4, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.at("/contentDownloadInfo").size());
        assertEquals(7, body.at("/contentDownloadInfo/0").size());
        assertEquals("1", body.at("/contentDownloadInfo/0/resourceType").textValue());
        assertEquals(TEST_TONE_ID, body.at("/contentDownloadInfo/0/resourceId").textValue());
        assertEquals("601520000000000016", body.at("/contentDownloadInfo/0/resourceCode").textValue());
        assertEquals("5000", body.at("/contentDownloadInfo/0/chargedPrice").textValue());
        assertTrue(body.at("/contentDownloadInfo/0/chargeTime").isTextual());
        assertEquals("60", body.at("/contentDownloadInfo/0/duration").textValue());
        assertTrue(body.at("/contentDownloadInfo/0/relativeExpiryDate").isTextual());
    }

    @Test
    void sendSm() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.system().sendSm(TEST_MOBILE, "478722"));
        assertNotNull(body);
        assertEquals(3, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
    }
}