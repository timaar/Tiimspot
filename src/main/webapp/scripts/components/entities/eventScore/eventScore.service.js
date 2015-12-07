'use strict';

angular.module('tiimspotApp')
    .factory('EventScore', function ($resource, DateUtils) {
        return $resource('api/eventScores/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
