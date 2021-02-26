package com.liao47.controller;

import com.github.liao47.common.exception.CustomException;
import com.github.liao47.union.UnionPayService;
import com.github.liao47.union.config.UnionProp;
import com.github.liao47.union.enums.UnionRespCodeEnum;
import com.github.liao47.union.model.PayNotifyDTO;
import com.github.liao47.union.model.RefundNotifyDTO;
import com.github.liao47.union.model.req.PayReq;
import com.github.liao47.union.model.req.QueryReq;
import com.github.liao47.union.model.req.RefundReq;
import com.github.liao47.union.model.resp.PayResp;
import com.github.liao47.union.model.resp.QueryResp;
import com.github.liao47.union.model.resp.RefundResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 银联支付测试
 * @author liao47
 * @date 2021/2/26 11:05
 */
@Slf4j
@Controller
@RequestMapping("unionpay")
public class UnionPayController {

    @Resource
    private UnionPayService unionPayService;

    /**
     * 支付
     * @param response
     * @return
     */
    @GetMapping("pay")
    public void pay(HttpServletResponse response) {
        PayReq payReq = new PayReq();
        UnionProp unionProp = getProp();
        payReq.setOrderId("TEST" + System.currentTimeMillis());
        payReq.setOrderDesc("测试订单");
        payReq.setTxnAmt("1");
        PayResp resp = unionPayService.pay(payReq, unionProp);

        try {
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            response.getWriter().print(resp.getHtml());
        } catch (Exception e) {
            log.error("银联支付异常:", e);
        }
    }

    /**
     * 查询
     * @param orderId
     * @return
     */
    @PostMapping("query")
    @ResponseBody
    public QueryResp query(@RequestParam String orderId) {
        UnionProp unionProp = getProp();
        QueryReq queryReq = new QueryReq();
        queryReq.setOrderId(orderId);
        return unionPayService.query(queryReq, unionProp);
    }

    /**
     * 退款
     * @param queryId
     * @return
     */
    @PostMapping("refund")
    @ResponseBody
    public RefundResp refund(@RequestParam String queryId) {
        UnionProp unionProp = getProp();
        RefundReq refundReq = new RefundReq();
        refundReq.setOrderId("REFUND" + System.currentTimeMillis());
        refundReq.setOrigQryId(queryId);
        refundReq.setTxnAmt("1");

        return unionPayService.refund(refundReq, unionProp);
    }

    /**
     * 支付回调通知
     * @return
     */
    @PostMapping("payNotify")
    public ResponseEntity<String> payNotify() {
        try {
            PayNotifyDTO dto = unionPayService.payNotify();
            log.info("Pay notify dto:[{}]", dto);
            if (UnionRespCodeEnum.CODE_00.getCode().equals(dto.getRespCode())) {
                log.info("Pay success:{}", dto.getRespMsg());
            } else {
                log.info("Pay fail:{}", dto.getRespMsg());
            }
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 退款回调通知
     * @return
     */
    @PostMapping("refundNotify")
    public ResponseEntity<String> refundNotify() {
        try {
            RefundNotifyDTO dto = unionPayService.refundNotify();
            log.info("Refund notify dto:[{}]", dto);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 商户配置<br>
     *     可配置于数据库，读取数据库配置
     * @return
     */
    public UnionProp getProp() {
        UnionProp unionProp = new UnionProp();
        unionProp.setMerId("777290058183840");
        unionProp.setSignCertPwd("000000");
        unionProp.setSignCertPath("acp_test_sign.pfx");
        return unionProp;
    }
}
