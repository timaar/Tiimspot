'use strict';

angular.module('tiimspotApp')
    .factory('EventScoreSearch', function ($resource) {
        return $resource('api/_search/eventScores/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
