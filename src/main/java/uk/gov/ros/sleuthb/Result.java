package uk.gov.ros.sleuthb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.springframework.cloud.sleuth.Span;

public class Result {
    String service;
    @SerializedName(Span.TRACE_ID_NAME)
    String traceId;
    @SerializedName(Span.PARENT_ID_NAME)
    String parentSpanId;
    @SerializedName(Span.SPAN_ID_NAME)
    String spanId;
    boolean exportable;

    Result(String serviceName, Span span) {
        service = serviceName;
        traceId = Long.toHexString(span.getTraceId());
        if (span.getParents().size() > 0)
            parentSpanId = Long.toHexString(span.getParents().get(0));
        spanId = Long.toHexString(span.getSpanId());
        exportable = span.isExportable();
    }

    @Override
    public String toString() {
        return new GsonBuilder().serializeNulls().create().toJson(this);
    }
}
