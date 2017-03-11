package parameter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by pjh on 2017/3/9.
 */
public class FormatParameter {

    private GetParameter getParameter = new GetParameter();

    public JSONArray format(){
        JSONArray meter = getParameter.queryMeter();
        JSONArray meterRegConf = getParameter.queryMeterRegConf();
        JSONArray countReg = getParameter.countReg();
        JSONArray regAddrStart = getParameter.queryRegAddrStart();
        if(meter == null || meterRegConf == null || countReg == null || regAddrStart == null){
            return null;
        }
        for(int i = 0; i < meter.size(); i++){
            for(int j = 0; j < regAddrStart.size(); j++){
                if (regAddrStart.getJSONObject(j).getIntValue("meterId") == meter.getJSONObject(i).getIntValue("meterId")){
                    meter.getJSONObject(i).fluentPut("regAddrStart", regAddrStart.getJSONObject(j).getString("regAddrStart"));
                }
            }
            for (int j = 0; j < countReg.size(); j++){
                if(countReg.getJSONObject(j).getIntValue("meterId") == meter.getJSONObject(i).getIntValue("meterId")){
                    meter.getJSONObject(i).fluentPut("regCount", countReg.getJSONObject(j).getIntValue("regCount"));
                }
            }
            JSONArray indexList =  new JSONArray();
            for(int j = 0; j < meterRegConf.size(); j++){
                if(meterRegConf.getJSONObject(j).getIntValue("meterId") == meter.getJSONObject(i).getIntValue("meterId")){
                    JSONObject index = new JSONObject();
                    index.fluentPut("energyType", meterRegConf.getJSONObject(j).getString("typeName"));
                    index.fluentPut("regAddr", meterRegConf.getJSONObject(j).getString("regAddr"));
                    indexList.add(index);
                }
            }
            meter.getJSONObject(i).fluentPut("indexList", indexList);
        }
        return meter;
    }

    public JSONObject parameter(){
        JSONObject parameter = new JSONObject();
        parameter.fluentPut("bizName", "");
        JSONArray param = format();
        JSONArray content = new JSONArray();
        for(int i = 0; i < param.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.fluentPut("command", "get");
            jsonObject.fluentPut("parameters", param.getJSONObject(i));
            content.add(jsonObject);
        }
        parameter.fluentPut("content", content);
        return parameter;
    }
}
