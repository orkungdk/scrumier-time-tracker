package tr.com.ogedik.timetracker.services.integration;

/*
 * @author enes.erciyes
 */

import org.springframework.stereotype.Service;
import tr.com.ogedik.commons.abstraction.AbstractService;
import tr.com.ogedik.commons.constants.Services;
import tr.com.ogedik.commons.rest.request.client.HttpRestClient;
import tr.com.ogedik.commons.rest.request.client.helper.RequestURLDetails;
import tr.com.ogedik.commons.rest.request.model.CreateUpdateWorklogRequest;
import tr.com.ogedik.commons.rest.response.BoardsResponse;
import tr.com.ogedik.commons.rest.response.RestResponse;
import tr.com.ogedik.commons.rest.response.SprintResponse;
import tr.com.ogedik.commons.rest.response.model.JQLSearchResult;
import tr.com.ogedik.commons.util.MapUtils;

@Service
public class TimeTrackerIntegrationServiceImpl extends AbstractService
    implements TimeTrackerIntegrationService {

  @Override
  public JQLSearchResult getWorklogSearchResult(String username, String startDate, String endDate) {

    RequestURLDetails requestURLDetails =
        generateRequestInfo(
            Services.INTEGRATION,
            Services.Path.LOGGED_ISSUES,
            MapUtils.of("username", username, "startDate", startDate, "endDate", endDate));

    RestResponse<JQLSearchResult> searchResultResponse =
        HttpRestClient.doGet(requestURLDetails, JQLSearchResult.class);

    return resolve(searchResultResponse);
  }

  @Override
  public JQLSearchResult getRecentIssues() {
    RequestURLDetails requestURLDetails =
        generateRequestInfo(Services.INTEGRATION, Services.Path.ISSUES, null);

    RestResponse<JQLSearchResult> searchResultResponse =
        HttpRestClient.doGet(requestURLDetails, JQLSearchResult.class);

    return resolve(searchResultResponse);
  }

  @Override
  public JQLSearchResult getIssuesInASprintSearchResult(String sprintCode, String fields) {
    RequestURLDetails requestURLDetails =
        generateRequestInfo(
            Services.INTEGRATION,
            Services.Path.ISSUES_IN_SPRINT,
            MapUtils.of("sprintCode", sprintCode, "fields", fields));
    RestResponse<JQLSearchResult> searchResultResponse =
        HttpRestClient.doGet(requestURLDetails, JQLSearchResult.class);
    return resolve(searchResultResponse);
  }

  @Override
  public BoardsResponse getAllBoards() {
    RequestURLDetails requestURLDetails =
        generateRequestInfo(Services.INTEGRATION, Services.Path.BOARDS, null);
    RestResponse<BoardsResponse> response =
        HttpRestClient.doGet(requestURLDetails, BoardsResponse.class);
    return resolve(response);
  }

  @Override
  public SprintResponse getSprintsInABoard(String boardId) {
    RequestURLDetails requestURLDetails =
        generateRequestInfo(
            Services.INTEGRATION, Services.Path.SPRINTS, MapUtils.of("boardId", boardId));
    RestResponse<SprintResponse> response =
        HttpRestClient.doGet(requestURLDetails, SprintResponse.class);
    return resolve(response);
  }

  @Override
  public Boolean updateWorklog(CreateUpdateWorklogRequest updateWorklogRequest) {
    RequestURLDetails requestURLDetails =
        generateRequestInfo(Services.INTEGRATION, Services.Path.WORKLOG, null);

    RestResponse<Boolean> response =
        HttpRestClient.doPut(requestURLDetails, updateWorklogRequest, Boolean.class);

    return resolve(response);
  }

  @Override
  public Boolean createWorklog(CreateUpdateWorklogRequest createWorklogRequest) {
    RequestURLDetails requestURLDetails =
        generateRequestInfo(Services.INTEGRATION, Services.Path.WORKLOG, null);

    RestResponse<Boolean> response =
        HttpRestClient.doPost(requestURLDetails, createWorklogRequest, Boolean.class);

    return resolve(response);
  }
}
