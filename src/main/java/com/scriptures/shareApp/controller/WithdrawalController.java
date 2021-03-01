package com.scriptures.shareApp.controller;

import com.scriptures.shareApp.controller.request.WithdrawalAddRequestBean;
import com.scriptures.shareApp.controller.request.WithdrawalUpdateRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Withdrawal;
import com.scriptures.shareApp.service.WithdrawalService;
import com.scriptures.shareApp.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Api(description = "佣金返利操作接口", produces = "application/json")
@RestController
@RequestMapping("/Withdrawal/")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @ApiOperation(value = "发起一个提现", notes = "发起一个提现")
    @PutMapping(value = "add.do")
    public ResponseEntity<String> add(@RequestBody WithdrawalAddRequestBean bean) {
        return withdrawalService.add(bean);
    }

    @ApiOperation(value = "修改一个提现状态", notes = "修改一个提现状态（0，待处理，1，已到账）")
    @PostMapping(value = "update.do")
    public ResponseEntity<String> update(@RequestBody WithdrawalUpdateRequestBean bean) {
        return withdrawalService.update(bean);
    }

    @ApiOperation(value = "批量修改提现状态", notes = "批量提现状态（0，待处理，1，已到账）")
    @PostMapping(value = "statusUptLis.do")
    public ResponseEntity<String> statusUptList(@RequestParam String ids, @RequestParam String updateBy) {
        return withdrawalService.statusUptList(ids, updateBy);
    }

    @ApiOperation(value = "批量修改提现状态(涉嫌刷单)", notes = "批量提现状态（0，待处理，1，已到账）")
    @PostMapping(value = "statusIllegal.do")
    public ResponseEntity<String> statusIllegal(@RequestParam String ids, @RequestParam String updateBy) {
        return withdrawalService.statusIllegal(ids, updateBy);
    }

    @ApiOperation(value = "显示所有提现列表", notes = "分页查找所有佣金返利账单")
    @GetMapping(value = "pageGetAll.do")
    public PageResponseBean<Withdrawal> pageGetAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return withdrawalService.pageWithdrawals(pageNum, pageSize);
    }

    @ApiOperation(value = "按条件显示所有提现列表", notes = "分页模糊查找佣金返利账单（前后台共用）")
    @GetMapping(value = "pageSearchLike.do")
    public PageResponseBean<Withdrawal> pageSearchLike(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String key, @RequestParam String alipay, @RequestParam Integer status, @RequestParam String startDay, @RequestParam String endDay) {
        return withdrawalService.pageWithdrawalsLike(pageNum, pageSize, key, alipay, status, startDay, endDay);
    }

    @ApiOperation(value = "按状态显示所有", notes = "分页模糊查找佣金返利账单（前后台共用）")
    @GetMapping(value = "likeStatus.do")
    public PageResponseBean<Withdrawal> likeStatus(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String status) {
        return withdrawalService.likeStatus(pageNum, pageSize, status);
    }

    @RequestMapping(value = "ExcelDownloadsAll", method = RequestMethod.GET)
    public void downloadAllClassmate(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        List<Withdrawal> classmateList = withdrawalService.downloadExcel();

        String fileName = "ShareApp-WithdrawalDataAll.xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = {"流水号", "会员昵称", "会员手机号", "发起提现时间", "提现金额", "提现状态", "支付宝账号", "支付宝姓名"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (Withdrawal teacher : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(teacher.getId());
            row1.createCell(1).setCellValue(teacher.getRemarks());
            row1.createCell(2).setCellValue(teacher.getCreateBy());
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            row1.createCell(3).setCellValue(sdf.format(teacher.getCreateDate()));
            row1.createCell(4).setCellValue(teacher.getWithdrawal() + "元");
            String status = "已到账";
            if (teacher.getStatus() == 0) {
                status = "待处理";
            }else if (teacher.getStatus() == 2) {
                status = "涉嫌刷单！！";
            }
            row1.createCell(5).setCellValue(status);
            row1.createCell(6).setCellValue(teacher.getAlipayAccount());
            row1.createCell(7).setCellValue(teacher.getAlipayName());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @RequestMapping(value = "ExcelDownloadsByStatus", method = RequestMethod.GET)
    public void downloadByStatusClassmate(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        List<Withdrawal> classmateList = withdrawalService.downloadExcel();

        String fileName = "ShareApp-WithdrawalDataByStatus.xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = {"流水号", "会员昵称", "会员手机号", "发起提现时间", "提现金额", "提现状态", "支付宝账号", "支付宝姓名"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (Withdrawal teacher : classmateList) {
            if (teacher.getStatus() == 0) {
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.createCell(0).setCellValue(teacher.getId());
                row1.createCell(1).setCellValue(teacher.getRemarks());
                row1.createCell(2).setCellValue(teacher.getCreateBy());
                DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                row1.createCell(3).setCellValue(sdf.format(teacher.getCreateDate()));
                row1.createCell(4).setCellValue(teacher.getWithdrawal() + "元");
                row1.createCell(5).setCellValue("待处理");
                row1.createCell(6).setCellValue(teacher.getAlipayAccount());
                row1.createCell(7).setCellValue(teacher.getAlipayName());
                rowNum++;
            }
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
