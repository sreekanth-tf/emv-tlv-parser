package com.tf.emvparser.services;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.BASE24Packager;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.tlv.TLVList;
import org.jpos.tlv.TLVMsg;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class EmvParserService {

    public String parseInput(String input) throws ISOException {
        //02003220000000808000000010000000001500120604120000000112340001840
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(new ISO87APackager());
        isoMsg.unpack(input.getBytes());
        return printISOMessage(isoMsg);
    }

    public void printData(ISOMsg isoMsg){
        //EMV data on DE55
        if (isoMsg.hasField(55)) {
            TLVList tlvData = new TLVList();
            tlvData.unpack(isoMsg.getBytes(55));
            for (TLVMsg tLVMsg : tlvData.getTags()) {
                System.out.println("EMVtag : " + Integer.toHexString(tLVMsg.getTag()));
                System.out.println("Value on String Type : " + ISOUtil.hexString(tLVMsg.getValue()));
            }
        }
    }

    private String printISOMessage(ISOMsg isoMsg) {
        printData(isoMsg);
        StringBuilder isoMsgOut = new StringBuilder(100);
        for (int i = 1; i <= isoMsg.getMaxField(); i++) {
            if (isoMsg.hasField(i)) {
                isoMsgOut.append(format("Field (%s) = %s%n", i, isoMsg.getString(i)));
            }
        }
        return isoMsgOut.toString();
    }
}
