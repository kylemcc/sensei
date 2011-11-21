package com.sensei.search.query;

import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sensei.search.query.filters.FilterConstructor;


public class UIDQueryConstructor extends QueryConstructor
{
  public static final String QUERY_TYPE = "ids";

  @Override
  protected Query doConstructQuery(JSONObject jsonQuery) throws JSONException
  {
    JSONObject filterJson = new JSONObject();
    filterJson.put(QUERY_TYPE, jsonQuery);

    Filter filter = null;
    try
    {
      filter = FilterConstructor.constructFilter(filterJson, null/* Analyzer is not used by this filter */);
    }
    catch(Exception e)
    {
      throw new JSONException(e);
    }
    ConstantScoreQuery query = new ConstantScoreQuery(filter);
    query.setBoost((float)jsonQuery.optDouble("boost", 1.0));
    return query;
  }
}
