public class test {


}
/*
 	@GetMapping(value = "/{id}")
	public String getDocument(@PathVariable(value = "id") int id, Model md) throws Exception {
		document document = documentService.getDocumentById(id);
		if (document != null) {
			List<feedback> listFeedback = feedbackService.getFeedBackByIdDocument(id);
			Object result = feedbackService.avgStarDocument(id);
			if (result == null) {
				md.addAttribute("star", 0);
			} else {
				double temp = (double) result;
				long star = Math.round(temp);
				md.addAttribute("star", star);
			}

			List<cosinesimilarity> listCosineSimilarity = cosineSimilarityService.getAllCosineSimilarity();

			// cosineByName
			cosinesimilarity cosineByName = cosineSimilarityService
					.getCosineSimilarityByKey(listCosineSimilarity.get(0).getKey());
			
			
			// cosineBySummary
			cosinesimilarity cosineBySummary = cosineSimilarityService
					.getCosineSimilarityByKey(listCosineSimilarity.get(1).getKey());
			
			
			// Convert String to Json: CosineByName And CosineBySummary
			JSONObject getJsonCosineByName = new JSONObject(cosineByName.getCosineSimilarity());
			JSONObject getJsonCosineBySummary = new JSONObject(cosineBySummary.getCosineSimilarity());
			
			
			try {
				JSONObject getCosineByIdOfJsonByName = getJsonCosineByName.getJSONObject(String.valueOf(id));
				JSONObject getCosineByIdOfJsonBySummary = getJsonCosineBySummary.getJSONObject(String.valueOf(id));
				
				
				// sort by value
				HashMap<String, Double> tempResultRecommendByName = new HashMap<String, Double>();
				HashMap<String, Double> tempResultRecommendBySummary = new HashMap<String, Double>();
				
				
				Iterator<String> keys1 = getCosineByIdOfJsonByName.keys();
				Iterator<String> keys2 = getCosineByIdOfJsonBySummary.keys();
				
				//sort value cosine by name
				while (keys1.hasNext()) {
					String key1 = keys1.next();
					if (key1.equalsIgnoreCase(String.valueOf(id))) {
						continue;
					}
					String tempValue1 = getCosineByIdOfJsonByName.get(key1).toString();
					double covertValue1 = Double.valueOf(tempValue1);
					tempResultRecommendByName.put(key1, covertValue1);

				}
				
				//sort value cosine by summary
				while (keys2.hasNext()) {
					String key2 = keys2.next();
					if (key2.equalsIgnoreCase(String.valueOf(id))) {
						continue;
					}
					String tempValue2 = getCosineByIdOfJsonBySummary.get(key2).toString();
					double covertValue2 = Double.valueOf(tempValue2);
					tempResultRecommendBySummary.put(key2, covertValue2);

				}

				HashMap<String, Double> resultRecommendByName = sortByValue(tempResultRecommendByName);
				String[] arrayIdDocumentCosineName = resultRecommendByName.keySet().toArray(new String[0]);
				
				HashMap<String, Double> resultRecommendBySummary = sortByValue(tempResultRecommendBySummary);
				String[] arrayIdDocumentCosineSummary = resultRecommendBySummary.keySet().toArray(new String[0]);
				
				
				
				//contain 3 id document of cosine by name and 3 id document of cosine by summary
				HashMap<Integer, Integer> tempListResult = new HashMap<Integer, Integer>();
				
				//add document of cosine by name
				for (int i = 0; i < 3; i++) {
					tempListResult.put(Integer.valueOf(arrayIdDocumentCosineName[i]), Integer.valueOf(arrayIdDocumentCosineName[i]));
				}
				
				//add document of cosine by summary
				for (int i = 0; i < arrayIdDocumentCosineSummary.length; i++) {
					if(tempListResult.size() == 6) {
						break;
					}//check id summary exist in tempResult
					else if (tempListResult.get(Integer.valueOf(arrayIdDocumentCosineSummary[i])) == null){
						tempListResult.put(Integer.valueOf(arrayIdDocumentCosineSummary[i]), Integer.valueOf(arrayIdDocumentCosineSummary[i]));
					}
					
				}
				
				List<document> listRecommendDocument = new ArrayList<document>();	
				
				for(Map.Entry<Integer, Integer> entry : tempListResult.entrySet()) {
					listRecommendDocument.add(documentService.getDocumentById(entry.getKey()));
				}
				
	
				
				md.addAttribute("listRecommendDocument", listRecommendDocument);
				md.addAttribute("listFeedback", listFeedback);
				md.addAttribute("document", document);

				return "document";
				}catch (Exception e) {
					md.addAttribute("listFeedback", listFeedback);
					md.addAttribute("document", document);
					return "document";
				}
		}
		return "403";
	}*/
