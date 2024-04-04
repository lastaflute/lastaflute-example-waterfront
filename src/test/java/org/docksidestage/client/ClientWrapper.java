package org.docksidestage.client;

import java.util.Map;

import org.opensearch.action.ActionRequest;
import org.opensearch.action.ActionType;
import org.opensearch.action.admin.indices.segments.IndicesSegmentResponse;
import org.opensearch.action.admin.indices.segments.PitSegmentsRequest;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.action.bulk.BulkRequestBuilder;
import org.opensearch.action.bulk.BulkResponse;
import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.action.delete.DeleteRequestBuilder;
import org.opensearch.action.delete.DeleteResponse;
import org.opensearch.action.explain.ExplainRequest;
import org.opensearch.action.explain.ExplainRequestBuilder;
import org.opensearch.action.explain.ExplainResponse;
import org.opensearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.opensearch.action.fieldcaps.FieldCapabilitiesRequestBuilder;
import org.opensearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.get.GetRequestBuilder;
import org.opensearch.action.get.GetResponse;
import org.opensearch.action.get.MultiGetRequest;
import org.opensearch.action.get.MultiGetRequestBuilder;
import org.opensearch.action.get.MultiGetResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexRequestBuilder;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.action.search.ClearScrollRequest;
import org.opensearch.action.search.ClearScrollRequestBuilder;
import org.opensearch.action.search.ClearScrollResponse;
import org.opensearch.action.search.CreatePitRequest;
import org.opensearch.action.search.CreatePitResponse;
import org.opensearch.action.search.DeletePitRequest;
import org.opensearch.action.search.DeletePitResponse;
import org.opensearch.action.search.GetAllPitNodesRequest;
import org.opensearch.action.search.GetAllPitNodesResponse;
import org.opensearch.action.search.MultiSearchRequest;
import org.opensearch.action.search.MultiSearchRequestBuilder;
import org.opensearch.action.search.MultiSearchResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchRequestBuilder;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.action.search.SearchScrollRequest;
import org.opensearch.action.search.SearchScrollRequestBuilder;
import org.opensearch.action.termvectors.MultiTermVectorsRequest;
import org.opensearch.action.termvectors.MultiTermVectorsRequestBuilder;
import org.opensearch.action.termvectors.MultiTermVectorsResponse;
import org.opensearch.action.termvectors.TermVectorsRequest;
import org.opensearch.action.termvectors.TermVectorsRequestBuilder;
import org.opensearch.action.termvectors.TermVectorsResponse;
import org.opensearch.action.update.UpdateRequest;
import org.opensearch.action.update.UpdateRequestBuilder;
import org.opensearch.action.update.UpdateResponse;
import org.opensearch.client.AdminClient;
import org.opensearch.client.Client;
import org.opensearch.common.settings.Settings;
import org.opensearch.threadpool.ThreadPool;

public class ClientWrapper implements Client {
    private Client parent;

    public void setClient(final Client parent) {
        this.parent = parent;
    }

    @Override
    public void close() {
        parent.close();
    }

    public <Request extends ActionRequest, Response extends org.opensearch.core.action.ActionResponse> org.opensearch.common.action.ActionFuture<Response> execute(
            ActionType<Response> action, Request request) {
        return parent.execute(action, request);
    }

    public <Request extends ActionRequest, Response extends org.opensearch.core.action.ActionResponse> void execute(
            ActionType<Response> action, Request request, org.opensearch.core.action.ActionListener<Response> listener) {
        parent.execute(action, request, listener);
    }

    public ThreadPool threadPool() {
        return parent.threadPool();
    }

    public AdminClient admin() {
        return parent.admin();
    }

    public org.opensearch.common.action.ActionFuture<IndexResponse> index(IndexRequest request) {
        return parent.index(request);
    }

    public void index(IndexRequest request, org.opensearch.core.action.ActionListener<IndexResponse> listener) {
        parent.index(request, listener);
    }

    public IndexRequestBuilder prepareIndex() {
        return parent.prepareIndex();
    }

    public IndexRequestBuilder prepareIndex(String index) {
        return parent.prepareIndex(index);
    }

    public org.opensearch.common.action.ActionFuture<UpdateResponse> update(UpdateRequest request) {
        return parent.update(request);
    }

    public void update(UpdateRequest request, org.opensearch.core.action.ActionListener<UpdateResponse> listener) {
        parent.update(request, listener);
    }

    public UpdateRequestBuilder prepareUpdate() {
        return parent.prepareUpdate();
    }

    public UpdateRequestBuilder prepareUpdate(String index, String id) {
        return parent.prepareUpdate(index, id);
    }

    public org.opensearch.common.action.ActionFuture<DeleteResponse> delete(DeleteRequest request) {
        return parent.delete(request);
    }

    public void delete(DeleteRequest request, org.opensearch.core.action.ActionListener<DeleteResponse> listener) {
        parent.delete(request, listener);
    }

    public DeleteRequestBuilder prepareDelete() {
        return parent.prepareDelete();
    }

    public DeleteRequestBuilder prepareDelete(String index, String id) {
        return parent.prepareDelete(index, id);
    }

    public org.opensearch.common.action.ActionFuture<BulkResponse> bulk(BulkRequest request) {
        return parent.bulk(request);
    }

    public void bulk(BulkRequest request, org.opensearch.core.action.ActionListener<BulkResponse> listener) {
        parent.bulk(request, listener);
    }

    public BulkRequestBuilder prepareBulk() {
        return parent.prepareBulk();
    }

    public BulkRequestBuilder prepareBulk(String globalIndex) {
        return parent.prepareBulk(globalIndex);
    }

    public org.opensearch.common.action.ActionFuture<GetResponse> get(GetRequest request) {
        return parent.get(request);
    }

    public void get(GetRequest request, org.opensearch.core.action.ActionListener<GetResponse> listener) {
        parent.get(request, listener);
    }

    public GetRequestBuilder prepareGet() {
        return parent.prepareGet();
    }

    public GetRequestBuilder prepareGet(String index, String id) {
        return parent.prepareGet(index, id);
    }

    public org.opensearch.common.action.ActionFuture<MultiGetResponse> multiGet(MultiGetRequest request) {
        return parent.multiGet(request);
    }

    public void multiGet(MultiGetRequest request, org.opensearch.core.action.ActionListener<MultiGetResponse> listener) {
        parent.multiGet(request, listener);
    }

    public MultiGetRequestBuilder prepareMultiGet() {
        return parent.prepareMultiGet();
    }

    public org.opensearch.common.action.ActionFuture<SearchResponse> search(SearchRequest request) {
        return parent.search(request);
    }

    public void search(SearchRequest request, org.opensearch.core.action.ActionListener<SearchResponse> listener) {
        parent.search(request, listener);
    }

    public SearchRequestBuilder prepareSearch(String... indices) {
        return parent.prepareSearch(indices);
    }

    public org.opensearch.common.action.ActionFuture<SearchResponse> searchScroll(SearchScrollRequest request) {
        return parent.searchScroll(request);
    }

    public void searchScroll(SearchScrollRequest request, org.opensearch.core.action.ActionListener<SearchResponse> listener) {
        parent.searchScroll(request, listener);
    }

    public SearchScrollRequestBuilder prepareSearchScroll(String scrollId) {
        return parent.prepareSearchScroll(scrollId);
    }

    public void createPit(CreatePitRequest createPITRequest, org.opensearch.core.action.ActionListener<CreatePitResponse> listener) {
        parent.createPit(createPITRequest, listener);
    }

    public void deletePits(DeletePitRequest deletePITRequest, org.opensearch.core.action.ActionListener<DeletePitResponse> listener) {
        parent.deletePits(deletePITRequest, listener);
    }

    public void getAllPits(GetAllPitNodesRequest getAllPitNodesRequest,
            org.opensearch.core.action.ActionListener<GetAllPitNodesResponse> listener) {
        parent.getAllPits(getAllPitNodesRequest, listener);
    }

    public void pitSegments(PitSegmentsRequest pitSegmentsRequest,
            org.opensearch.core.action.ActionListener<IndicesSegmentResponse> listener) {
        parent.pitSegments(pitSegmentsRequest, listener);
    }

    public org.opensearch.common.action.ActionFuture<MultiSearchResponse> multiSearch(MultiSearchRequest request) {
        return parent.multiSearch(request);
    }

    public void multiSearch(MultiSearchRequest request, org.opensearch.core.action.ActionListener<MultiSearchResponse> listener) {
        parent.multiSearch(request, listener);
    }

    public MultiSearchRequestBuilder prepareMultiSearch() {
        return parent.prepareMultiSearch();
    }

    public org.opensearch.common.action.ActionFuture<TermVectorsResponse> termVectors(TermVectorsRequest request) {
        return parent.termVectors(request);
    }

    public void termVectors(TermVectorsRequest request, org.opensearch.core.action.ActionListener<TermVectorsResponse> listener) {
        parent.termVectors(request, listener);
    }

    public TermVectorsRequestBuilder prepareTermVectors() {
        return parent.prepareTermVectors();
    }

    public TermVectorsRequestBuilder prepareTermVectors(String index, String id) {
        return parent.prepareTermVectors(index, id);
    }

    public org.opensearch.common.action.ActionFuture<MultiTermVectorsResponse> multiTermVectors(MultiTermVectorsRequest request) {
        return parent.multiTermVectors(request);
    }

    public void multiTermVectors(MultiTermVectorsRequest request,
            org.opensearch.core.action.ActionListener<MultiTermVectorsResponse> listener) {
        parent.multiTermVectors(request, listener);
    }

    public MultiTermVectorsRequestBuilder prepareMultiTermVectors() {
        return parent.prepareMultiTermVectors();
    }

    public ExplainRequestBuilder prepareExplain(String index, String id) {
        return parent.prepareExplain(index, id);
    }

    public org.opensearch.common.action.ActionFuture<ExplainResponse> explain(ExplainRequest request) {
        return parent.explain(request);
    }

    public void explain(ExplainRequest request, org.opensearch.core.action.ActionListener<ExplainResponse> listener) {
        parent.explain(request, listener);
    }

    public ClearScrollRequestBuilder prepareClearScroll() {
        return parent.prepareClearScroll();
    }

    public org.opensearch.common.action.ActionFuture<ClearScrollResponse> clearScroll(ClearScrollRequest request) {
        return parent.clearScroll(request);
    }

    public void clearScroll(ClearScrollRequest request, org.opensearch.core.action.ActionListener<ClearScrollResponse> listener) {
        parent.clearScroll(request, listener);
    }

    public FieldCapabilitiesRequestBuilder prepareFieldCaps(String... indices) {
        return parent.prepareFieldCaps(indices);
    }

    public org.opensearch.common.action.ActionFuture<FieldCapabilitiesResponse> fieldCaps(FieldCapabilitiesRequest request) {
        return parent.fieldCaps(request);
    }

    public void fieldCaps(FieldCapabilitiesRequest request, org.opensearch.core.action.ActionListener<FieldCapabilitiesResponse> listener) {
        parent.fieldCaps(request, listener);
    }

    public Settings settings() {
        return parent.settings();
    }

    public Client filterWithHeader(Map<String, String> headers) {
        return parent.filterWithHeader(headers);
    }

    public Client getRemoteClusterClient(String clusterAlias) {
        return parent.getRemoteClusterClient(clusterAlias);
    }

}
