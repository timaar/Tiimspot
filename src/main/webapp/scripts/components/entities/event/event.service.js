'use strict';

angular.module('tiimspotApp')
    .factory('Event', function ($resource, DateUtils) {
        return $resource('api/events/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.datum = DateUtils.convertDateTimeFromServer(data.datum);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
