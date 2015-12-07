 'use strict';

angular.module('tiimspotApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-tiimspotApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-tiimspotApp-params')});
                }
                return response;
            }
        };
    });
